package net.novatech.jbprotocol.tcp;

import net.novatech.jbprotocol.java.JavaSession;
import io.netty.channel.ChannelHandlerContext;

public class TcpClientSession extends TcpSession {

	private TcpClient client;
	
	public TcpClientSession(TcpClient client) {
		this.client = client;
	}
	
	@Override
	public void customChannelInactive(ChannelHandlerContext ctx) throws Exception {
		JavaSession session = (JavaSession)this.client.mainClient.getSession();
		
		this.client.mainClient.getClientListener().sessionDisconnected(session, "Session disconnected");
		this.client.mainClient.setSession(null);
	}

}
