package net.novatech.jbprotocol;

import java.net.InetSocketAddress;
import java.net.SocketException;

import io.gomint.jraknet.ServerSocket;
import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.bedrock.BedrockSession;
import net.novatech.jbprotocol.listener.ServerListener;
import net.novatech.jbprotocol.tcp.TcpServer;

public class ProtocolServer {
	
	@Getter
	private String host;
	@Getter
	private int port;
	@Getter
	private GameEdition gameProtocol;
	@Getter
	@Setter
	private int maxConnections;
	@Setter
	@Getter
	private ServerListener serverListener;
	
	private TcpServer tcpServer;
	private ServerSocket udpServer;
	
	public ProtocolServer(InetSocketAddress address, GameEdition protocolType) {
		this(address.getAddress().toString(), address.getPort(), protocolType);
	}
	
	public ProtocolServer(String host, int port, GameEdition protocolType) {
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
	
	public void close() {
		switch(this.gameProtocol) {
		case JAVA:
			this.tcpServer.close();
			break;
		case BEDROCK:
			this.udpServer.close();
			break;
		}
	}
	
	private void bindJava() {
		this.tcpServer = new TcpServer(this);
		tcpServer.initialize();
		tcpServer.bind();
	}
	
	private void bindBedrock() {
		this.udpServer = new ServerSocket(getMaxConnections());
		udpServer.setEventHandler(new SocketEventHandler() {
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
			udpServer.bind(getHost(), getPort());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}