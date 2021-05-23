package net.novatech.jbprotocol.tcp;

import java.net.InetSocketAddress;
import net.novatech.jbprotocol.ProtocolClient;
import net.novatech.jbprotocol.java.JavaSession;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;

public class TcpClient {
	
	public ProtocolClient mainClient;
	@Getter
	private Bootstrap tcpSocket;
	
	public TcpClient(ProtocolClient mainClient) {
		this.mainClient = mainClient;
	}
	
	public void initialize() {
		this.tcpSocket = new Bootstrap()
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<Channel>() {
					public void initChannel(Channel channel) {
						TcpSession nettySession = new TcpClientSession(TcpClient.this);
						JavaSession session = new JavaSession(nettySession, true);
						channel.config().setOption(ChannelOption.TCP_NODELAY, false);
						
						channel.pipeline().addLast("session", nettySession);
						
						mainClient.setSession(session);
					}
				});
	}
	
	public void connect(InetSocketAddress address) {
		this.tcpSocket.bind(address.getAddress(), address.getPort());
	}
	
}