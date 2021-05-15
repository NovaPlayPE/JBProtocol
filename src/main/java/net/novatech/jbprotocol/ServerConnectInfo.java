package net.novatech.jbprotocol;

import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

public class ServerConnectInfo {
	
	@Getter
	public InetSocketAddress address;
	@Getter
	@Setter
	public GameVersion gameProtocol;
	
	public ServerConnectInfo(InetSocketAddress address) {
		this.address = address;
	}
	
}