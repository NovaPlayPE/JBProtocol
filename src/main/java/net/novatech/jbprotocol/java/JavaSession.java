package net.novatech.jbprotocol.java;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.GameSession;
import net.novatech.jbprotocol.MinecraftProtocol;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.listener.GameListener;
import net.novatech.jbprotocol.listener.LoginListener;
import net.novatech.jbprotocol.packet.AbstractPacket;
import net.novatech.jbprotocol.tcp.TcpSession;
import net.novatech.library.utils.ByteBufUtils;

public class JavaSession implements GameSession {

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
	@Setter
	private TcpSession mcConnection;
	
	private boolean authRequired = false;
	
	public JavaSession(TcpSession mcConnection) {
		this(mcConnection, false);
	}
	
	public JavaSession(TcpSession mcConnection, boolean isClient) {
		this.mcConnection = mcConnection;
		if(this.protocol == null) {
			this.protocol = new JavaProtocol(isClient);
		}
	}
	
	public void requireAuthentication(boolean value) {
		this.authRequired = value;
	}

	@Override
	public void sendPacket(AbstractPacket pk) {
		this.mcConnection.sendPacket(pk);
	}

	@Override
	public void tick(int currentTick) {
		while(this.mcConnection.receivePacket() != null) {
			ByteBuf buf = this.mcConnection.receivePacket();
			int id = ByteBufUtils.readUnsignedVarInt(buf);
			if(getProtocol() instanceof JavaProtocol) {
				JavaProtocol protocol = (JavaProtocol)getProtocol();
				AbstractPacket packet = protocol.createPacket((byte)id);
				if(packet == null) {
					throw new NullPointerException("Packet with id " + id + " does not exist");
				}
				JavaPacket pk = (JavaPacket)packet;
				try {
					pk.read(buf);
				} catch (Exception e) {}
				if(protocol.getGameState() == JavaGameState.GAME) {
					getGameListener().receivePacket(pk);
				}
			}
		}
	}

}
