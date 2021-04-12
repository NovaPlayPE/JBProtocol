package net.novatech.protocol;

import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

public class ServerConnectInfo {
	
	@Getter
	public InetSocketAddress address;
	@Getter
	@Setter
	public GameProtocol gameProtocol;
	
	public ServerConnectInfo(InetSocketAddress address) {
		this.address = address;
	}
	
}