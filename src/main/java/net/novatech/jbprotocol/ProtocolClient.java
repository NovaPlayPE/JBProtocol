package net.novatech.jbprotocol;

import java.net.InetSocketAddress;
import java.net.SocketException;

import io.gomint.jraknet.ClientSocket;
import io.gomint.jraknet.Connection;
import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.bedrock.BedrockSession;
import net.novatech.jbprotocol.listener.ClientListener;
import net.novatech.jbprotocol.packet.AbstractPacket;
import net.novatech.jbprotocol.tcp.TcpClient;
import net.novatech.jbprotocol.tcp.TcpSession;

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
	@Setter
	private GameSession session;
	@Getter
	@Setter
	private ClientListener clientListener;
	
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
		TcpClient client = new TcpClient(this);
		client.initialize();
		client.connect(getConnectedServer().getAddress());
	}
	
	private void createBedrockConnection() {
		ClientSocket client = new ClientSocket();
		client.setEventHandler(new SocketEventHandler() {

			@Override
			public void onSocketEvent(Socket socket, SocketEvent event) {
				ProtocolClient.this.session = new BedrockSession(event.getConnection(), true);
				switch(event.getType()) {
				case CONNECTION_ATTEMPT_FAILED:
					getClientListener().sessionFailed(ProtocolClient.this.session, "Session failed to connect: " + event.getReason());
					break;
				case CONNECTION_CLOSED:
				case CONNECTION_DISCONNECTED:
					getClientListener().sessionDisconnected(ProtocolClient.this.session, "Session dissconected: " + event.getReason());
					break;
				case CONNECTION_ATTEMPT_SUCCEEDED:
					getClientListener().sessionConnected(ProtocolClient.this.session);
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