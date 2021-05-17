package net.novatech.jbprotocol;

import net.novatech.jbprotocol.listener.GameListener;
import net.novatech.jbprotocol.listener.LoginListener;
import net.novatech.jbprotocol.packet.AbstractPacket;

public interface GameSession {
	
	void setLoginListener(LoginListener listener);
	LoginListener getLoginListener();
	
	void setGameListener(GameListener listener);
	GameListener getGameListener();
	
	MinecraftProtocol getProtocol();
	
	void sendPacket(AbstractPacket pk);
	void tick(int currentTick);
	
	void requireAuthentication(boolean auth);
	
}