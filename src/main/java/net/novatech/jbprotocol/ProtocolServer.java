package net.novatech.jbprotocol;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.gomint.jraknet.ServerSocket;
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
import net.novatech.jbprotocol.bedrock.data.BedrockPong;
import net.novatech.jbprotocol.data.Pong;
import net.novatech.jbprotocol.listener.ServerListener;
import net.novatech.jbprotocol.tcp.TcpServer;
import net.novatech.jbprotocol.util.MessageConsumer;

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
	@Getter
	private Pong pong; 
	@Getter
	private static ProtocolServer instance;
	public final EventLoopGroup eventLoop;
	@Getter
	private static final Logger logger = LoggerFactory.getLogger(ProtocolServer.class);
	
	private TcpServer tcpServer;
	private ServerSocket udpServer;
	
	private List<BedrockSession> bedrockSessions = new ArrayList<BedrockSession>();
	
	public ProtocolServer(InetSocketAddress address, GameEdition protocolType) {
		this(address.getAddress().toString(), address.getPort(), protocolType);
	}
	
	public ProtocolServer(String host, int port, GameEdition protocolType) {
		instance = this;
		
		this.host = host;
		this.port = port;
		this.gameProtocol = protocolType;
		this.pong = protocolType.getInitialPong();
		this.eventLoop = Epoll.isAvailable() ? new EpollEventLoopGroup(0, r -> {return new Thread(r, "ProtocolServer");}) 
				: new NioEventLoopGroup(0, r -> {return new Thread(r, "ProtocolServer");});
		this.eventLoop.scheduleAtFixedRate(this::tick, 50, 50, TimeUnit.MILLISECONDS);
	}
	
	public void bind(MessageConsumer consumer) {
		switch(this.gameProtocol) {
		case JAVA:
			bindJava(consumer);
			break;
		case BEDROCK:
			bindBedrock(consumer);
			break;
		}
	}
	
	public void close() {
		switch(this.gameProtocol) {
		case JAVA -> this.tcpServer.close();
		case BEDROCK -> this.udpServer.close();
		}
	}
	
	public void tick() {
		if(getGameProtocol() == GameEdition.JAVA) {
			this.tcpServer.tick();
		} else if(getGameProtocol() == GameEdition.BEDROCK){
			this.bedrockSessions.forEach(s -> {s.tick();});
		}
	}
	
	private void bindJava(MessageConsumer consumer) {
		this.tcpServer = new TcpServer(this);
		tcpServer.initialize();
		tcpServer.bind(consumer);
	}
	
	private void bindBedrock(MessageConsumer consumer) {
		this.udpServer = new ServerSocket(getLogger(), getMaxConnections());
		udpServer.setEventHandler(new SocketEventHandler() {
			@Override
			public void onSocketEvent(Socket socket, SocketEvent event) {
				BedrockSession session = new BedrockSession(event.getConnection());
				switch(event.getType()) {
				case NEW_INCOMING_CONNECTION:
					getServerListener().sessionConnected(session);
					bedrockSessions.add(session);
					break;
				case CONNECTION_CLOSED:
				case CONNECTION_DISCONNECTED:
					getServerListener().sessionDisconnected(session, "Session dissconected: " + event.getReason());
					bedrockSessions.remove(session);
					break;
				case UNCONNECTED_PING:
					BedrockPong pong = (BedrockPong)getPong();
					getServerListener().handlePong(pong);
					StringJoiner motd = new StringJoiner(";")
							.add("MCPE")
							.add(pong.motd)
							.add(String.valueOf(pong.protocolVersion))
							.add(pong.gameVersion)
							.add(String.valueOf(pong.onlinePlayers))
							.add(String.valueOf(pong.maxPlayers))
							.add(String.valueOf(udpServer.getGuid()))
							.add(pong.subMotd)
							.add(pong.gamemode)
							.add("1");
					event.getPingPongInfo().setMotd(motd.toString());
					break;
				}
			}
			
		});
		udpServer.setMojangModificationEnabled(true);
		try {
			udpServer.bind(getHost(), getPort());
			consumer.success();
		} catch (SocketException e) {
			consumer.failed(e);
		}
	}
	
	
	
}
