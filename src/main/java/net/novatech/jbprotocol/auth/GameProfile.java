package net.novatech.jbprotocol.auth;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

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
	
	public static GameProfile fromJSON(String json) {
		JSONObject obj = new JSONObject(json);
		UUID uuid = UUID.fromString(obj.getString("id"));
		if(uuid != null) {
			String username = obj.getString("name");
			JSONArray prop1 = obj.getJSONArray("properties");
			ArrayList<JSONObject> list = Property.toList(prop1);
			Property[] properties = Property.listToArray(list);
			
			return new GameProfile(uuid, username, properties);
		}
		return null;
	}
	
	public static String toJSON(GameProfile profile) {
		JSONObject json = new JSONObject();
		json.put("name", profile.getUsername());
		json.put("id", profile.getUuid());
		json.put("properties", Property.serialize(profile.getProperties()));
		
		return json.toString();
	}
	
}