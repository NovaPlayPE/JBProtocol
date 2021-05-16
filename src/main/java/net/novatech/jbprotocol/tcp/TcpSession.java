package net.novatech.jbprotocol.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.novatech.jbprotocol.packet.AbstractPacket;

public class TcpSession extends SimpleChannelInboundHandler<AbstractPacket>{

	private Channel channel = null;
	
	private AbstractPacket caught = null;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AbstractPacket msg) throws Exception {
		this.caught = msg;
	}
	
	public AbstractPacket receivePacket() {
		return this.caught;
	}
	
	public void sendPacket(AbstractPacket packet) {
		if(this.channel == null) {
			return;
		}
		ChannelFuture future = this.channel.writeAndFlush(packet, this.channel.voidPromise());
		if(!future.isSuccess()) {
			System.out.println("Failed to write packet, reason: " + future.cause());
		}
	}

}
