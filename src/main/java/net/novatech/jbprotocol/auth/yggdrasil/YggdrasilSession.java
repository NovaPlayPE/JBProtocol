package net.novatech.jbprotocol.auth.yggdrasil;

import lombok.Getter;
import net.novatech.jbprotocol.auth.AuthSession;
import net.novatech.jbprotocol.auth.GameProfile;
import net.novatech.jbprotocol.auth.Property;
import net.novatech.jbprotocol.auth.User;

public class YggdrasilSession extends AuthSession {
	
	@Getter
	private Property[] properties;
	@Getter
	private String clientToken;
	@Getter
	private String accessToken;
	@Getter
	private GameProfile profile;
	@Getter
	private GameProfile[] availableProfiles;
	
	public YggdrasilSession(User user, String clientToken, String accessToken, GameProfile selectedProfile, GameProfile[] profiles) {
		this.properties = user.getProperties();
		this.clientToken = clientToken;
		this.accessToken = accessToken;
		this.profile = selectedProfile;
		this.availableProfiles = profiles;
	}
	
}