package net.novatech.jbprotocol;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import io.gomint.jraknet.ClientSocket;
import io.gomint.jraknet.Connection;
import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.bedrock.BedrockSession;
import net.novatech.jbprotocol.listener.ClientListener;
import net.novatech.jbprotocol.packet.AbstractPacket;
import net.novatech.jbprotocol.tcp.TcpClient;
import net.novatech.jbprotocol.tcp.TcpSession;
import net.novatech.jbprotocol.util.MessageConsumer;

public class ProtocolClient{
	
	@Getter
	private String host;
	@Getter
	private int port;
	@Getter
	private GameEdition gameProtocol;
	@Getter
	private ServerConnectInfo connectedServer = null;
	@Getter
	@Setter
	private GameSession session;
	@Getter
	@Setter
	private ClientListener clientListener;
	public final EventLoopGroup eventLoop;
	
	public ProtocolClient(InetSocketAddress address, GameEdition protocolType) {
		this(address.getAddress().getHostAddress(), address.getPort(), protocolType);
	}
	
	public ProtocolClient(String host, int port, GameEdition protocolType) {
		this.host = host;
		this.port = port;
		this.gameProtocol = protocolType;
		this.eventLoop = Epoll.isAvailable() ? new EpollEventLoopGroup(0, r -> {return new Thread(r, "ProtocolClient");}) 
				: new NioEventLoopGroup(0, r -> {return new Thread(r, "ProtocolClient");});
		this.eventLoop.scheduleAtFixedRate(this::tick, 50, 50, TimeUnit.MILLISECONDS);
	}
	
	public void connectTo(ServerConnectInfo info, MessageConsumer consumer) {
		info.setGameProtocol(gameProtocol);
		this.connectedServer = info;
		handleConnection(consumer);
	}
	
	public void sendPacket(AbstractPacket packet) {
		this.getSession().sendPacket(packet);
	}
	
	public void tick() {
		if(getSession() != null) {
			getSession().tick();
		}
	}
	
	private void handleConnection(MessageConsumer consumer) {
		switch(gameProtocol) {
		case BEDROCK -> createBedrockConnection(consumer);
		case JAVA -> createJavaConnection(consumer);
		}
	}
	
	private void createJavaConnection(MessageConsumer consumer) {
		TcpClient client = new TcpClient(this);
		client.initialize();
		client.connect(getConnectedServer().getAddress(), consumer);
	}
	
	private void createBedrockConnection(MessageConsumer consumer) {
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
				default:
					break;
				}
				
			}
			
		});
		try {
			client.initialize();
			client.connect(getConnectedServer().getAddress());
			consumer.success();
		} catch(SocketException ex) {
			consumer.failed(ex);
		}
			
	}
	
}