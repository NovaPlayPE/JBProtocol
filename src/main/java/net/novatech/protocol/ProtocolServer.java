package net.novatech.protocol;

import java.net.InetSocketAddress;
import java.net.SocketException;

import io.gomint.jraknet.ServerSocket;
import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import lombok.Getter;
import lombok.Setter;
import net.novatech.protocol.bedrock.BedrockSession;

public class ProtocolServer {
	
	@Getter
	private String host;
	@Getter
	private int port;
	@Getter
	private GameVersion gameProtocol;
	@Getter
	@Setter
	private int maxConnections;
	@Setter
	@Getter
	private ServerListener serverListener;
	
	public ProtocolServer(InetSocketAddress address, GameVersion protocolType) {
		this(address.getAddress().toString(), address.getPort(), protocolType);
	}
	
	public ProtocolServer(String host, int port, GameVersion protocolType) {
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
				BedrockSession session = new BedrockSession(event.getConnection());
				switch(event.getType()) {
				case NEW_INCOMING_CONNECTION:
					getServerListener().sessionConnected(session);
					break;
				case CONNECTION_CLOSED:
				case CONNECTION_DISCONNECTED:
					getServerListener().sessionDisconnected(session, "Session dissconected: " + event.getReason());
					break;
				case UNCONNECTED_PING:
					break;
				}
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