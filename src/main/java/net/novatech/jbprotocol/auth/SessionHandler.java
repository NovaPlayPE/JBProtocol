package net.novatech.jbprotocol.auth;

import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import net.novatech.library.net.NetworkUtils;

public class SessionHandler {
	
	public static URL SESSION_URL = NetworkUtils.toURL("https://sessionserver.mojang.com/session/minecraft/");
	public static URL SESSION_JOIN_URL = NetworkUtils.toURL("https://sessionserver.mojang.com/session/minecraft/join");
	
	public static void joinServer(String serverId, String accessToken, UUID uuid) {
		JSONObject object = new JSONObject();
		object.put("accessToken", accessToken);
		object.put("selectedProfile", uuid);
		object.put("serverId", serverId);
		
		String response = AuthService.doRequest(SESSION_JOIN_URL, object);
	}
	
	public static GameProfile getProfile(String name, String serverId) {
		String uri = "https://sessionserver.mojang.com/session/minecraft/hasJoined?username="+name+"?serverId="+serverId;
		URL url = NetworkUtils.toURL(uri);
		
		String response = AuthService.doRequest(url, null);
		if(response != null) {
			return GameProfile.fromJSON(response);
		}
		return null;
	}
	
}