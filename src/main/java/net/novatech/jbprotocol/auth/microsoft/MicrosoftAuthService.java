package net.novatech.jbprotocol.auth.microsoft;

import java.io.IOException;

import net.novatech.jbprotocol.auth.AuthService;
import net.novatech.jbprotocol.auth.AuthSession;

public class MicrosoftAuthService extends AuthService {
	
	public String authCode;
	
	public MicrosoftAuthService(String authCode) {
		this.authCode = authCode;
	}
	
	@Override
	public AuthSession authenticate() throws IOException {
		return null;
	}

}
