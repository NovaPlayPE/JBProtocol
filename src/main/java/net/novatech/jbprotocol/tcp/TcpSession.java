package net.novatech.jbprotocol.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.novatech.jbprotocol.packet.AbstractPacket;
import lombok.Getter;

public abstract class TcpSession extends SimpleChannelInboundHandler<ByteBuf>{
	
	@Getter
	protected Channel channel = null;
	private boolean disconnected = false;
	
	protected ByteBuf caught = null;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		this.caught = msg;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		customChannelInactive(ctx);
	}
	
	public abstract void customChannelInactive(ChannelHandlerContext ctx) throws Exception;
	
	public ByteBuf receivePacket() {
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
	
	public void disconnect(String reason) {
		disconnect(reason, null);
	}
	
	public void disconnect(String reason, Throwable cause) {
		if(this.disconnected) return;
		this.disconnected = true;
		
		if(this.channel != null && this.channel.isOpen()) {
			this.channel.flush().close().addListener((ChannelFutureListener) future ->{
				System.out.println("Connection closed: " + reason != null ? reason : cause);
			});
		}
		this.channel = null;
	}

}
