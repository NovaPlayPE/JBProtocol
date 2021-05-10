package net.novatech.protocol;

import java.net.InetSocketAddress;
import java.net.SocketException;

import io.gomint.jraknet.ClientSocket;
import io.gomint.jraknet.Connection;
import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import lombok.Getter;
import lombok.Setter;
import net.novatech.protocol.bedrock.BedrockSession;
import net.novatech.protocol.packet.AbstractPacket;
import net.novatech.protocol.tcp.TcpClient;

public class ProtocolClient{
	
	@Getter
	private String host;
	@Getter
	private int port;
	@Getter
	private GameVersion gameProtocol;
	@Getter
	private ServerConnectInfo connectedServer = null;
	@Getter
	private GameSession session;
	
	public ProtocolClient(InetSocketAddress address, GameVersion protocolType) {
		this(address.getAddress().toString(), address.getPort(), protocolType);
	}
	
	public ProtocolClient(String host, int port, GameVersion protocolType) {
		this.host = host;
		this.port = port;
		this.gameProtocol = protocolType;
	}
	
	public void connectTo(ServerConnectInfo info) {
		info.setGameProtocol(gameProtocol);
		this.connectedServer = info;
		handleConnection();
	}
	
	public void sendPacket(AbstractPacket packet) {
		this.getSession().sendPacket(packet);
	}
	
	private void handleConnection() {
		switch(gameProtocol) {
		case BEDROCK:
			createBedrockConnection();
			break;
		case JAVA:
			createJavaConnection();
			break;
		}
	}
	
	private void createJavaConnection() {
		TcpClient client = new TcpClient();
		client.initialize();
		client.connect(getConnectedServer().getAddress());
	}
	
	private void createBedrockConnection() {
		ClientSocket client = new ClientSocket();
		client.setEventHandler(new SocketEventHandler() {

			@Override
			public void onSocketEvent(Socket socket, SocketEvent event) {
				switch(event.getType()) {
				case CONNECTION_ATTEMPT_FAILED:
					break;
				case CONNECTION_CLOSED:
				case CONNECTION_DISCONNECTED:
					break;
				case CONNECTION_ATTEMPT_SUCCEEDED:
					ProtocolClient.this.session = new BedrockSession(event.getConnection());
					break;
				}
				
			}
			
		});
		try {
			client.initialize();
		} catch(SocketException ex) {}
		client.connect(getConnectedServer().getAddress());
	}
	
}