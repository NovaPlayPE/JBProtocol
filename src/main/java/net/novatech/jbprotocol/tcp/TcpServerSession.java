package net.novatech.jbprotocol.tcp;

import io.netty.channel.ChannelHandlerContext;
import net.novatech.jbprotocol.java.JavaSession;

public class TcpServerSession extends TcpSession {

	private TcpServer server;
	
	public TcpServerSession(TcpServer server) {
		this.server = server;
	}
	
	@Override
	public void customChannelInactive(ChannelHandlerContext ctx) throws Exception {
		JavaSession session = this.server.searchByTcp(this);
		
		this.server.sessions.remove(session);
		this.server.mainServer.getServerListener().sessionDisconnected(session, "Session dissconnected");
		
	}
	
}