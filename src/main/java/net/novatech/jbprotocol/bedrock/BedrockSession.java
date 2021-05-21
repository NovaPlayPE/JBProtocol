package net.novatech.jbprotocol.bedrock;

import io.gomint.jraknet.Connection;
import io.gomint.jraknet.EncapsulatedPacket;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.jraknet.PacketReliability;
import io.gomint.jraknet.SocketEvent;
import net.novatech.jbprotocol.GameSession;
import net.novatech.jbprotocol.MinecraftProtocol;
import net.novatech.jbprotocol.bedrock.packets.BedrockPacket;
import net.novatech.jbprotocol.bedrock.packets.LoginPacket;
import net.novatech.jbprotocol.bedrock.packets.PlayStatusPacket;
import net.novatech.jbprotocol.listener.GameListener;
import net.novatech.jbprotocol.listener.LoginListener;
import net.novatech.jbprotocol.packet.AbstractPacket;
import net.novatech.jbprotocol.util.SessionData;
import net.novatech.library.utils.ByteBufUtils;
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
	
	private SessionData sessionData = null;
	
	private Connection connection;
	private boolean authRequired;
	
	public BedrockSession(Connection connection) {
		this(connection, false);
	}
	
	public BedrockSession(Connection connection, boolean client) {
		this.connection = connection;
		if(this.protocol == null) {
			this.protocol = new BedrockProtocol(client);
		}
	}
	
	public void requireAuthentication(boolean value) {
		this.authRequired = value;
	}
	
	public void tick(int currentTick) {
		while(this.connection.receive() != null) {
			EncapsulatedPacket enc = this.connection.receive();
			PacketBuffer pB = new PacketBuffer(enc.getPacketData());
			enc.release();
			
			if(pB.getRemaining() <= 0) return;
			while(pB.getRemaining() > 0) {
				int length = ByteBufUtils.readUnsignedVarInt(pB.getBuffer());
				int pos = pB.getReadPosition();
				try {
					int id = handleBody(pB, length + pos, currentTick);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private int handleBody(PacketBuffer pB, int skipPos, int currentTick) throws Exception {
		int raw = ByteBufUtils.readUnsignedVarInt(pB.getBuffer());
		int pid = raw & 0x3FF;
		
		BedrockPacket pk = (BedrockPacket) getProtocol().createPacket((byte)pid);
		pk.read(pB.getBuffer());
		this.receivePacket(pk);
		return pid;
	}
	
	private void receivePacket(AbstractPacket pk) {
		if(!getProtocol().isClient()) {
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
				SessionData data = new BedrockSessionData();
				
				playStatus.status = PlayStatusPacket.Status.LOGIN_SUCCESS;
				sendPacket(playStatus);
				
				this.loginListener.loginCompleted(data);
				return;
			}
		}
		
		this.gameListener.receivePacket(pk);
	}

	@Override
	public void sendPacket(AbstractPacket pk) {
		if(pk instanceof BedrockPacket) {
			PacketBuffer pB = new PacketBuffer(64);
			try {
				ByteBufUtils.writeUnsignedVarInt(pB.getBuffer(), pk.getId());
				pk.write(pB.getBuffer());
				this.connection.send(PacketReliability.RELIABLE_ORDERED, pB);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			throw new IllegalArgumentException("Packet must override BedrockPacket, but given " + pk.getClass().getName());
		}
	}
	
	

}
