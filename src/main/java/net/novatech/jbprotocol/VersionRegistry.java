package net.novatech.jbprotocol;

import java.util.*;

public class VersionRegistry {
	
	private GameEdition edition;
	public VersionRegistry(GameEdition edition) {
		this.edition = edition;
	}
	
	public  Map<Integer, MinecraftProtocol> protocolRegistry = new HashMap<Integer,MinecraftProtocol>();
	public void addProtocol(int protocolVersion, MinecraftProtocol protocol) {
		protocolRegistry.put(protocolVersion,protocol);
	}
	
	public  MinecraftProtocol searchProtocol(int version) {
		if(protocolRegistry.get(version) != null) return protocolRegistry.get(version);
		return null;
	}
	
}