package net.novatech.jbprotocol.listener;

import net.novatech.jbprotocol.GameSession;

public abstract class ClientListener {
	
	public abstract void sessionConnected(GameSession session);
	public abstract void sessionDisconnected(GameSession session, String cause);
	public abstract void sessionFailed(GameSession session, String cause);
	
}