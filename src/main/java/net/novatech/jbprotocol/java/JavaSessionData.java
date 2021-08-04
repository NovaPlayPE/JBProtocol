package net.novatech.jbprotocol.java;

import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.auth.GameProfile;
import net.novatech.jbprotocol.auth.Property;
import net.novatech.jbprotocol.util.SessionData;

public class JavaSessionData extends SessionData {
	
	@Getter
	@Setter
	private Property[] properties;
	
	public GameProfile toGameProfile() {
		return new GameProfile(this.getUuid(), this.getUsername(), getProperties());
	}
	
}