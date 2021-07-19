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
		ArrayList<JSONObject> aProperties = Property.toList(jsonProperties);
		Property[] properties = Property.listToArray(aProperties);
		String id = obj.getString("id");
		
		return new User(username, properties, id);
	}
	
}
