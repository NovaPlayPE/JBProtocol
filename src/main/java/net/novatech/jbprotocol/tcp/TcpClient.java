package net.novatech.jbprotocol.tcp;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;

public class TcpClient {
	
	@Getter
	private Bootstrap tcpSocket;
	
	public void initialize() {
		this.tcpSocket = new Bootstrap()
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<Channel>() {
					public void initChannel(Channel channel) {
						channel.config().setOption(ChannelOption.TCP_NODELAY, false);
						
						
					}
				});
	}
	
	public void connect(InetSocketAddress address) {
		this.tcpSocket.bind(address.getAddress(), address.getPort());
	}
	
}