package net.novatech.jbprotocol.listener;

import net.novatech.jbprotocol.GameSession;
import net.novatech.jbprotocol.data.Pong;

public abstract class ServerListener {
	
	public abstract void sessionConnected(GameSession session);
	public abstract void sessionDisconnected(GameSession session, String cause);
	public abstract void handlePong(Pong pong);
	
}