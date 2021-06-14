package net.novatech.jbprotocol.bedrock.packets.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.Getter;
import net.novatech.jbprotocol.bedrock.BedrockSessionData;

public class ChainData {
	
	private byte[] payload;
	@Getter
	private BedrockSessionData sessionData;
	
	public ChainData(byte[] payload) {
		this.payload = payload;
		this.sessionData = new BedrockSessionData();
		decode();
	}
	
	private void decode() {
		ByteBuffer buf = ByteBuffer.wrap(this.payload);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		byte[] stringData = new byte[buf.getInt()];
		buf.get(stringData);
		
		String jwtParse = new String(stringData);
		JSONObject jwt = new JSONObject(jwtParse);
		
		//chain data
		if(jwt.isEmpty() || !(jwt.get("chain") instanceof JSONArray)) {
			return;
		}
		JSONArray chainData = jwt.getJSONArray("chain");
		boolean xboxAuthed = isAuthed(chainData);
		for(int i = 0; i < chainData.length(); i++) {
			JSONObject chainMap = decodeToken(chainData.getString(i));
			if(chainMap.has("extraData")) {
				JSONObject obj = chainMap.getJSONObject("extraData");
				String username = obj.getString("displayName");
				UUID uuid = UUID.fromString(obj.getString("identity"));
				String xuid = obj.getString("XUID");
				getSessionData().setUsername(username);
				getSessionData().setUuid(uuid);
			}
		}
		
		//skin data
	}
	
	private boolean isAuthed(JSONArray chains) {
		PublicKey key = null;
		boolean authed = false;
		for(int i = 0; i < chains.length(); i++) {
			
		}
		return false;
	}

	
	private JSONObject decodeToken(String token) {
		String[] splitter = token.split("\\.");
		if(splitter.length != 3) {
			return null;
		}
		String json = new String(Base64.getDecoder().decode(splitter[1]), StandardCharsets.UTF_8);
		return new JSONObject(json);
	}
	
}