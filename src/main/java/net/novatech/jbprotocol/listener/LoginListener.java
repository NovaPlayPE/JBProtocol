package net.novatech.jbprotocol.listener;

import io.gomint.jraknet.Connection;
import lombok.Getter;
import net.novatech.jbprotocol.util.SessionData;

public abstract class LoginListener {
	
	public abstract void loginCompleted(SessionData data);
	
}