package net.novatech.protocol;

import java.net.InetSocketAddress;
import java.net.SocketException;

import io.gomint.jraknet.ServerSocket;
import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import lombok.Getter;
import lombok.Setter;

public class ProtocolServer implements GameSession{
	
	@Getter
	private String host;
	@Getter
	private int port;
	@Getter
	private GameProtocol gameProtocol;
	@Getter
	@Setter
	private LoginListener loginListener;
	@Getter
	@Setter
	private GameListener gameListener;
	@Getter
	@Setter
	private int maxConnections;
	
	public ProtocolServer(InetSocketAddress address, GameProtocol protocolType) {
		this(address.getAddress().toString(), address.getPort(), protocolType);
	}
	
	public ProtocolServer(String host, int port, GameProtocol protocolType) {
		this.host = host;
		this.port = port;
		this.gameProtocol = protocolType;
	}
	
	public void bind() {
		switch(this.gameProtocol) {
		case JAVA:
			break;
		case BEDROCK:
			bindBedrock();
			break;
		}
	}
	
	private void bindJava() {
		
	}
	
	private void bindBedrock() {
		ServerSocket socket = new ServerSocket(getMaxConnections());
		socket.setEventHandler(new SocketEventHandler() {

			@Override
			public void onSocketEvent(Socket socket, SocketEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
		try {
			socket.bind(getHost(), getPort());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}