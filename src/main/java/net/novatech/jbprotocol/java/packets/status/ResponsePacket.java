package net.novatech.jbprotocol.java.packets.status;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.auth.GameProfile;
import net.novatech.jbprotocol.java.data.JavaPong;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.io.ByteBufUtils;

public class ResponsePacket extends JavaPacket {

	public JavaPong pong;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		JSONObject json = new JSONObject();
		
		JSONObject version = new JSONObject();
		version.put("name", pong.gameVersion);
		version.put("protocol", pong.protocolVersion);
		
		JSONObject players = new JSONObject();
		players.put("max", pong.maxPlayers);
		players.put("online", pong.onlinePlayers);
		if(pong.onlinePlayerList.length > 0) {
			JSONArray list = new JSONArray();
			for(GameProfile profile : pong.onlinePlayerList) {
				list.put(GameProfile.toJSON(profile));
			}
			players.put("sample", list);
		}
		
		json.put("version", version);
		json.put("players", players);
		json.put("description", pong.description);
		if(pong.icon != null) {
			json.put("favicon", serializeIcon(pong.icon));
		}
		ByteBufUtils.writeString(buf, json.toString());
		
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		JSONObject json = new JSONObject(ByteBufUtils.readString(buf));
		this.pong = new JavaPong();
		
		JSONObject version = json.getJSONObject("version");
		this.pong.gameVersion = version.getString("name");
		this.pong.protocolVersion = version.getInt("protocol");
		
		JSONObject players = json.getJSONObject("players");
		this.pong.maxPlayers = players.getInt("max");
		this.pong.onlinePlayers = players.getInt("online");
		if(players.getJSONArray("sample") != null) {
			JSONArray list = players.getJSONArray("sample");
			if(list.length() > 0) {
				this.pong.onlinePlayerList = new GameProfile[list.length()];
				for(int i = 0; i < list.length(); i++) {
					GameProfile profile = GameProfile.fromJSON(list.getString(i));
					this.pong.onlinePlayerList[i] = profile;
				}
			}
		}
		
		this.pong.description = json.getString("description");
		if(json.getString("favicon") != null) {
			this.pong.icon = deserializeIcon((byte[]) json.get("favicon"));
		}
		
	}
	
	private byte[] serializeIcon(String icon) {
		if(icon.startsWith("data:image/png;base64,")) {
			icon = icon.substring("data:image/png;base64,".length());
		}
		return Base64.getDecoder().decode(icon.getBytes(StandardCharsets.UTF_8));
	}
	
	private String deserializeIcon(byte[] icon) {
		return "data:image/png;base64," + new String(Base64.getEncoder().encode(icon), StandardCharsets.UTF_8);
	}

	@Override
	public boolean isServerBound() {
		return false;
	}

	@Override
	public boolean isClientBound() {
		return true;
	}

	@Override
	public byte getId() {
		return 0x00;
	}

}
