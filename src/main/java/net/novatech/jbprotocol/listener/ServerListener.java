package net.novatech.jbprotocol.listener;

import net.novatech.jbprotocol.GameSession;

public abstract class ServerListener {
	
	public abstract void sessionConnected(GameSession session);
	public abstract void sessionDisconnected(GameSession session, String cause);
	
}