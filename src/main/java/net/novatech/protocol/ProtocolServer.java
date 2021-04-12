package net.novatech.protocol;

import lombok.Getter;

public class ProtocolServer {
	
	@Getter
	private String host;
	@Getter
	private int port;
	@Getter
	private GameProtocol gameProtocol;
	@Getter
	private ServerLoginListener loginListener;
	@Getter
	private ServerGameListener gameListener;
	
	public ProtocolServer(String host, int port, GameProtocol protocolType) {
		this.host = host;
		this.port = port;
		this.gameProtocol = protocolType;
	}
	
	public void setLoginListener(ServerLoginListener listener) {
		this.loginListener = listener;
	}
	
	public void setGameListener(ServerGameListener listener) {
		this.gameListener = listener;
	}
	
	public void bind() {
		
	}
	
	
	
}