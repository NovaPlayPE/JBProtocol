package net.novatech.jbprotocol.java;

import net.novatech.jbprotocol.GameSession;
import net.novatech.jbprotocol.MinecraftProtocol;
import net.novatech.jbprotocol.listener.GameListener;
import net.novatech.jbprotocol.listener.LoginListener;
import net.novatech.jbprotocol.packet.AbstractPacket;

public class JavaSession implements GameSession {

	@Override
	public void setLoginListener(LoginListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public LoginListener getLoginListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGameListener(GameListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public GameListener getGameListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MinecraftProtocol getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendPacket(AbstractPacket pk) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tick(int currentTick) {
		// TODO Auto-generated method stub

	}

}
