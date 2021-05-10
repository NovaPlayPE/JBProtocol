package net.novatech.protocol;

import io.gomint.jraknet.Connection;
import lombok.Getter;
import net.novatech.protocol.util.SessionData;

public abstract class LoginListener {
	
	public abstract void loginCompleted(SessionData data);
	
}