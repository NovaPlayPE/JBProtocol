package net.novatech.jbprotocol.util;

import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.util.UUID;

public class SessionData {
	
	@Getter
	@Setter
	public String username = null;
	
	@Getter
	@Setter
	public UUID uuid = null;
	
	@Getter
	@Setter
	public InetSocketAddress address;
	
}