package net.novatech.jbprotocol.auth;

import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;

public class GameProfile {
	
	@Getter
	public UUID uuid;
	@Getter
	public String username;
	
	public GameProfile(@NonNull UUID uuid, @NonNull String username) {
		this.uuid = uuid;
		this.username = username;
	}
	
}