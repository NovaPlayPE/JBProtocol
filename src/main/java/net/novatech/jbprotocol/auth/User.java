package net.novatech.jbprotocol.auth;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.Getter;

public class User {
	
	@Getter
	public String username;
	@Getter
	public Property[] properties;
	@Getter
	public String id;
	
	public User(String username, Property[] properties, String id) {
		this.username = username;
		this.properties = properties;
		this.id = id;
	}
	
	public static User fromJSON(JSONObject obj) {
		String username = obj.getString("username");
		JSONArray jsonProperties = obj.getJSONArray("properties");
		ArrayList<JSONObject> aProperties = new ArrayList<JSONObject>();
		for(int i = 0; i < jsonProperties.length(); i++) {
			aProperties.add((JSONObject) jsonProperties.get(i));
		}
		Property[] properties = listToArray(aProperties);
		String id = obj.getString("id");
		
		return new User(username, properties, id);
	}
	
	private static Property[] listToArray(ArrayList<JSONObject> aProperties) {
		Property[] properties = new Property[aProperties.size()];
		int i = 0;
		for(JSONObject obj : aProperties) {
			properties[i] = new Property(obj.getString("name"), obj.getString("value"));
			i++;
		}
		return properties;
	}
	
}
