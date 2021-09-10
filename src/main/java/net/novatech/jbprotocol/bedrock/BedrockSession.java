package net.novatech.jbprotocol.bedrock;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import io.gomint.jraknet.Connection;
import io.gomint.jraknet.EncapsulatedPacket;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.jraknet.PacketReliability;
import io.gomint.jraknet.SocketEvent;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.PlatformDependent;
import net.novatech.jbprotocol.GameSession;
import net.novatech.jbprotocol.MinecraftProtocol;
import net.novatech.jbprotocol.bedrock.packets.BedrockPacket;
import net.novatech.jbprotocol.bedrock.packets.LoginPacket;
import net.novatech.jbprotocol.bedrock.packets.PlayStatusPacket;
import net.novatech.jbprotocol.bedrock.packets.Wrapper;
import net.novatech.jbprotocol.bedrock.packets.util.ChainData;
import net.novatech.jbprotocol.data.SessionData;
import net.novatech.jbprotocol.listener.GameListener;
import net.novatech.jbprotocol.listener.LoginListener;
import net.novatech.jbprotocol.listener.LoginServerListener;
import net.novatech.jbprotocol.listener.LoginClientListener;
import net.novatech.jbprotocol.packet.AbstractPacket;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.crypto.Processor;
import net.novatech.library.io.ByteBufUtils;
import lombok.Getter;
import lombok.Setter;

public class BedrockSession implements GameSession {

	@Getter
	@Setter
	public LoginListener loginListener;
	@Getter
	@Setter
	public GameListener gameListener;
	@Getter
	@Setter
	public MinecraftProtocol protocol = null;
	@Getter
	private BedrockSessionData sessionData = null;
	
	private Connection connection;
	private boolean authRequired;
	
	public Processor outputProcess = new Processor(true);
	public Processor inputProcess = new Processor(false);
	
	private Queue<BedrockPacket> queuedPacket = PlatformDependent.newMpscQueue();
	
	public BedrockSession(Connection connection) {
		this(connection, false);
	}
	
	public BedrockSession(Connection connection, boolean client) {
		this.connection = connection;
		this.connection.addDataProcessor(encapsulated -> {
			if(encapsulated.getPacketData().readableBytes() <= 0) {
				return encapsulated;
			}
			
			byte pid = encapsulated.getPacketData().readByte();
			if(pid == (byte)0xFF) {
				ByteBuf pure = receiveBatch(encapsulated.getPacketData());
				EncapsulatedPacket newPk = new EncapsulatedPacket();
				newPk.setPacketData(pure);
				pure.release();
				return newPk;
			}
			encapsulated.getPacketData().readerIndex(0);
			return encapsulated;
		});
		if(this.protocol == null) {
			this.protocol = new BedrockProtocol(client);
		}
		this.sessionData = new BedrockSessionData();
		this.sessionData.setAddress(this.connection.getAddress());
	}
	
	public void requireAuthentication(boolean value) {
		this.authRequired = value;
	}
	
	public void tick() {
		List<PacketBuffer> pBs = null;
		
		while(this.connection.receive() != null) {
			EncapsulatedPacket enc = this.connection.receive();
			PacketBuffer pB = new PacketBuffer(enc.getPacketData());
			if(pBs == null) {
				pBs = new ArrayList<PacketBuffer>();
			}
			pBs.add(pB);
			enc.release();
			
			if(pBs != null) {
				for(PacketBuffer buf : pBs) {
					try {
						if(buf.getRemaining() <= 0) return;
						while(pB.getRemaining() > 0) {
							int length = ByteBufUtils.readUnsignedVarInt(buf.getBuffer());
							int pos = buf.getReadPosition();
							try {
								int id = handleBody(buf, length + pos);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch(Exception ex) {}
				}
			}
		}
		
		while(this.queuedPacket.poll() != null) {
			BedrockPacket pk = this.queuedPacket.poll();
			if(!(pk instanceof Wrapper)) {
				PacketHelper.writeBatchPacket(this, new BedrockPacket[] {(BedrockPacket)pk});
			}
		}
	}
	
	private int handleBody(PacketBuffer pB, int skipPos) throws Exception {
		int raw = ByteBufUtils.readUnsignedVarInt(pB.getBuffer());
		int pid = raw & 0x3FF;
		
		if(pid == 0xFF) {
			return pid;
		}
		
		BedrockPacket pk = (BedrockPacket) getProtocol().createPacket((byte)pid);
		if(pk == null) {
			pB.setReadPosition(skipPos);
			return pid;
		}
		pk.read(pB.getBuffer());
		this.receivePacket(pk);
		return pid;
	}
	
	private ByteBuf receiveBatch(ByteBuf payload) {
		return this.inputProcess.process(payload);
	}
	
	private void receivePacket(AbstractPacket pk) {
		if(!getProtocol().isClient()) {
			handleServerSidePacket(pk);
		} else {
			handleClientSidePacket(pk);
		}
		this.gameListener.receivePacket(pk);
	}
	
	private void handleClientSidePacket(AbstractPacket pk) {
		
	}
	
	private void handleServerSidePacket(AbstractPacket pk) {
		if(pk instanceof LoginPacket) {
			LoginPacket login = (LoginPacket)pk;
			PlayStatusPacket playStatus = new PlayStatusPacket();
			if(login.protocolVersion > getProtocol().getProtocolVersion()) {
				playStatus.status = PlayStatusPacket.Status.FAILED_CLIENT;
				sendPacket(playStatus);
				return;
			} else if (login.protocolVersion < getProtocol().getProtocolVersion()){
				playStatus.status = PlayStatusPacket.Status.FAILED_SERVER;
				sendPacket(playStatus);
				return;
			}
			// handle login data
			byte[] payload = login.payload;
			ChainData chainHandler = new ChainData(payload);
			this.sessionData = chainHandler.getSessionData();
			
			playStatus.status = PlayStatusPacket.Status.LOGIN_SUCCESS;
			sendPacket(playStatus);
			LoginServerListener listener = (LoginServerListener)this.loginListener;
			listener.loginCompleted(getSessionData());
		}
	}

	@Override
	public void sendPacket(AbstractPacket pk) {
		if(pk instanceof BedrockPacket) {
			if(pk instanceof Wrapper){
				this.sendPacketImmediatly((BedrockPacket)pk);
			} else {
				this.queuedPacket.add((BedrockPacket)pk);
			}
			
		} else {
			throw new IllegalArgumentException("Packet must override BedrockPacket, but given " + pk.getClass().getName());
		}
	}
	
	public void sendPacketImmediatly(BedrockPacket pk) {
		PacketBuffer pB = new PacketBuffer(64);
		try {
			ByteBufUtils.writeUnsignedVarInt(pB.getBuffer(), pk.getId());
			pk.write(pB.getBuffer());
			this.connection.send(PacketReliability.RELIABLE_ORDERED, pB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	

}
