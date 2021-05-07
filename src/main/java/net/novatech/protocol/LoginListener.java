package net.novatech.protocol;

import io.gomint.jraknet.Connection;
import lombok.Getter;

public abstract class LoginListener {
	
	@Getter
	private GameSession session;
	@Getter
	private GameProtocol protocol;
	
	public LoginListener(GameSession session, GameProtocol protocol) {
		this.session = session;
	}
	
	public void handleBedrockLogin(Connection connection) {
		if(connection != null) {
			
		}
	}
	
	public void loginComplelted() {
		
	}
	
}