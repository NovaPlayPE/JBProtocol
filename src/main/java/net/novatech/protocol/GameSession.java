package net.novatech.protocol;

import net.novatech.protocol.packet.AbstractPacket;

public interface GameSession {
	
	void setLoginListener(LoginListener listener);
	LoginListener getLoginListener();
	
	void setGameListener(GameListener listener);
	GameListener getGameListener();
	
	MinecraftProtocol getProtocol();
	
	void sendPacket(AbstractPacket pk);
	void tick(int currentTick);
	
}