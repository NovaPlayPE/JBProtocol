package net.novatech.jbprotocol.tcp;

import net.novatech.jbprotocol.ProtocolServer;
import net.novatech.jbprotocol.java.JavaSession;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.*;

public class TcpServer {

	public ProtocolServer mainServer;
	private ServerBootstrap tcpSocket;
	private ChannelFuture future;
	public List<JavaSession> sessions = new ArrayList<JavaSession>();
	
	public TcpServer(ProtocolServer mainServer) {
		this.mainServer = mainServer;
	}

	public void initialize() {
		this.tcpSocket = new ServerBootstrap()
			
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<Channel>() {
					public void initChannel(Channel channel) {
						TcpSession nettySession = new TcpServerSession(TcpServer.this);
						JavaSession session = new JavaSession(nettySession);
						
						channel.config().setOption(ChannelOption.TCP_NODELAY, false);
						channel.pipeline().addLast("encoder", new TcpPacketEncoder());
						channel.pipeline().addLast("decoder", new TcpPacketDecoder());
						channel.pipeline().addLast("session", nettySession);
						
						TcpServer.this.mainServer.getServerListener().sessionConnected(session);

			}
					
		});
	}
	
	public void bind() {
		try {
			this.future = this.tcpSocket.bind(this.mainServer.getPort());
			this.future.sync().channel().closeFuture().syncUninterruptibly();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		if(this.future == null) {
			return;
		}
		for(JavaSession session : this.sessions) {
			session.getMcConnection().getChannel().close();
		}
		this.future = null;
		this.tcpSocket = null;
	}
	
	public JavaSession searchByTcp(TcpServerSession tcp) {
		for(JavaSession session : sessions){
			if(session.getMcConnection() == tcp) {
				return session;
			}
		}
		return null;
	}
	
}