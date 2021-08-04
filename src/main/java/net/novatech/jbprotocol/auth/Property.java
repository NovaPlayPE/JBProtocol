package net.novatech.jbprotocol.auth;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.Getter;

public class Property{
	
	@Getter
	private String property;
	@Getter
	private String value;
	@Getter
	private String signature;
	
	public Property(String property, String value) {
		this(property, value, null);
	}
	
	public Property(String property, String value, String signature) {
		this.property = property;
		this.value = value;
		this.signature = signature;
	}
	
	public static ArrayList<JSONObject> toList(JSONArray array){
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		for(int i = 0; i < array.length(); i++) {
			list.add((JSONObject)array.get(i));
		}
		return list;
	}
	
	public static Property[] listToArray(ArrayList<JSONObject> aProperties) {
		Property[] properties = new Property[aProperties.size()];
		int i = 0;
		for(JSONObject obj : aProperties) {
			properties[i] = new Property(obj.getString("name"), obj.getString("value"));
			i++;
		}
		return properties;
	}
	
	public static JSONArray serialize(Property[] properties){
		JSONArray array = new JSONArray();
		
		for(Property property : properties) {
			JSONObject obj = new JSONObject();
			obj.put("name", property.getProperty());
			obj.put("value", property.getValue());
			array.put(obj);
		}
		return array;
	}
	
}