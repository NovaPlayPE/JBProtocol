package net.novatech.protocol;

public interface GameSession {
	
	void setLoginListener(LoginListener listener);
	LoginListener getLoginListener();
	
	void setGameListener(GameListener listener);
	GameListener getGameListener();
	
}