package net.novatech.protocol;

import java.net.InetSocketAddress;
import java.net.SocketException;

import io.gomint.jraknet.ClientSocket;
import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import lombok.Getter;
import lombok.Setter;
import net.novatech.protocol.packet.AbstractPacket;
import net.novatech.protocol.tcp.TcpClient;

public class ProtocolClient{
	
	@Getter
	private String host;
	@Getter
	private int port;
	@Getter
	private GameProtocol gameProtocol;
	@Getter
	private ServerConnectInfo connectedServer = null;
	
	public ProtocolClient(InetSocketAddress address, GameProtocol protocolType) {
		this(address.getAddress().toString(), address.getPort(), protocolType);
	}
	
	public ProtocolClient(String host, int port, GameProtocol protocolType) {
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
				// TODO Auto-generated method stub
				
			}
			
		});
		try {
			client.initialize();
		} catch(SocketException ex) {}
		client.connect(getConnectedServer().getAddress());
	}
	
}