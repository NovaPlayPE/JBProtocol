package net.novatech.jbprotocol.auth;

import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;

public class GameProfile {
	
	@Getter
	public UUID uuid;
	@Getter
	public String username;
	@Getter
	public Property[] properties;
	
	public GameProfile(@NonNull UUID uuid, @NonNull String username) {
		this(uuid, username, null);
	}
	
	public GameProfile(@NonNull UUID uuid, @NonNull String username, Property[] properties) {
		this.uuid = uuid;
		this.username = username;
		this.properties = properties;
	}
	
}