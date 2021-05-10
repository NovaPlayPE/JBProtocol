package net.novatech.protocol.java;

import net.novatech.protocol.GameListener;
import net.novatech.protocol.GameSession;
import net.novatech.protocol.LoginListener;
import net.novatech.protocol.MinecraftProtocol;
import net.novatech.protocol.packet.AbstractPacket;

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
