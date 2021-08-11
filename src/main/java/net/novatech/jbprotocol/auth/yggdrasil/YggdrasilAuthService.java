package net.novatech.jbprotocol.auth.yggdrasil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import net.novatech.jbprotocol.auth.AuthService;
import net.novatech.jbprotocol.auth.AuthSession;
import net.novatech.jbprotocol.auth.GameProfile;
import net.novatech.jbprotocol.auth.User;
import net.novatech.library.net.NetworkUtils;

public class YggdrasilAuthService extends AuthService {
	
	public String name;
	public String password;
	public String accessToken;
	public String clientToken;
	
	public YggdrasilAuthService(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	@Override
	public AuthSession authenticate() throws IOException {
		JSONObject post = new JSONObject();
		post.put("agent", new JSONObject(){{
			put("name", "Minecraft");
			put("version", 1);
		}});
		post.put("username", this.name);
		post.put("pasword", this.password);
		post.put("clientToken", this.clientToken);
		post.put("requestUser", true);
		
		String response = tryAuth(post);
		return handleResponse(response);
	}
	
	public YggdrasilSession handleResponse(String response) {
		JSONObject map = new JSONObject(response);
		
		User user = User.fromJSON(map.getJSONObject("user"));
		String cToken = map.getString("clientToken");
		String aToken = map.getString("accessToken");
		GameProfile[] availableProfiles = getGameProfiles(map.getJSONArray("availableProfiles"));
		GameProfile selectedProfile = new GameProfile(
				UUID.fromString(map.getJSONObject("selectedProfile").getString("id")),
				map.getJSONObject("selectedProfile").getString("name")
				);
		return new YggdrasilSession(user, cToken, aToken, selectedProfile, availableProfiles);
	}
	
	private GameProfile[] getGameProfiles(JSONArray array) {
		ArrayList<JSONObject> jsons = new ArrayList<JSONObject>();
		for(int i = 0; i < array.length(); i++) {
			jsons.add(array.getJSONObject(i));
		}
		
		GameProfile[] profiles = new GameProfile[array.length()];
		int j = 0;
		for(JSONObject obj : jsons) {
			profiles[j] = new GameProfile(UUID.fromString(obj.getString("id")), obj.getString("name"));
			j++;
		}
		
		return profiles;
	}
	
	private String tryAuth(JSONObject post) throws IOException {
		return NetworkUtils.doPost(YGGDRASIL_AUTH_URL, post.toString(), "application/json");
	}

}
