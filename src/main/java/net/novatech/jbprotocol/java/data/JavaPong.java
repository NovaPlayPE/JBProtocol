package net.novatech.jbprotocol.java.data;

import net.novatech.jbprotocol.auth.GameProfile;
import net.novatech.jbprotocol.data.Pong;

public class JavaPong extends Pong {
	
	public String gameVersion;
	public int protocolVersion;
	public String description;
	public GameProfile[] onlinePlayerList;
	public String icon;
	
}