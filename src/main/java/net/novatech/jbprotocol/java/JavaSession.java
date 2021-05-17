package net.novatech.jbprotocol.java;

import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.GameSession;
import net.novatech.jbprotocol.MinecraftProtocol;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.listener.GameListener;
import net.novatech.jbprotocol.listener.LoginListener;
import net.novatech.jbprotocol.packet.AbstractPacket;
import net.novatech.jbprotocol.tcp.TcpSession;

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
	
	public JavaSession() {
		
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
			JavaPacket pk = (JavaPacket) this.mcConnection.receivePacket();
			if(getProtocol() instanceof JavaProtocol) {
				JavaProtocol protocol = (JavaProtocol)getProtocol();
				if(protocol.getGameState() == JavaGameState.LOGIN) {
					//to do login handler...
				} else if(protocol.getGameState() == JavaGameState.GAME) {
					getGameListener().receivePacket(pk);
				}
			}
		}
	}

}
