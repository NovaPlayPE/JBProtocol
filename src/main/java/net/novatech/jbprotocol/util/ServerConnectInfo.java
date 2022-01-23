package net.novatech.jbprotocol.util;

import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.GameEdition;

import java.net.InetSocketAddress;

public class ServerConnectInfo {
	
	@Getter
	public InetSocketAddress address;
	@Getter
	@Setter
	public GameEdition gameProtocol;
	
	public ServerConnectInfo(InetSocketAddress address) {
		this.address = address;
	}
	
}