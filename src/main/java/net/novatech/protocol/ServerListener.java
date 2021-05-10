package net.novatech.protocol;

public abstract class ServerListener {
	
	public abstract void sessionConnected(GameSession session);
	public abstract void sessionDisconnected(GameSession session, String cause);
	
}