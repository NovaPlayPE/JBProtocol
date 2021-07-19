package net.novatech.jbprotocol.tcp;

import java.net.InetSocketAddress;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.novatech.jbprotocol.packet.AbstractPacket;
import lombok.Getter;
import lombok.Setter;

public abstract class TcpSession extends SimpleChannelInboundHandler<ByteBuf>{
	
	@Getter
	protected Channel channel = null;
	private boolean disconnected = false;
	@Getter
	@Setter
	private InetSocketAddress address;
	@Getter
	@Setter
	private int compressionTreshold = 256;
	
	@Getter
	@Setter
	private SecretKey secretKey;
	private Cipher encryptionCipher;
	private Cipher decryptionCipher;
	
	protected ByteBuf caught = null;
	
	/*
	 * DEFAULT
	 */
	
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
	
	
	/*
	 * PACKET STUFF
	 */
	
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
	
	/*
	 * SESSION MAIN FUNCTIONS;
	 */
	
	public void updateCompressionTreshold() {
		if(this.channel != null) {
			if(this.getCompressionTreshold() < 0) {
				if(this.channel.pipeline().get("compress") != null) this.channel.pipeline().remove("compress");
				if(this.channel.pipeline().get("decompress") != null) this.channel.pipeline().remove("decompress");
				
				if(this.channel.pipeline().get("compress") == null) {
					this.channel.pipeline().addBefore("encoder", "compress", new TcpPacketCompressor(this));
				}
				if(this.channel.pipeline().get("decompress") == null) {
					this.channel.pipeline().addBefore("decoder", "decompress", new TcpPacketDecompressor(this));
				}
			}
		}
	}
	
	public void updateSecretKey() {
		if(this.channel != null && this.getSecretKey() != null) {
			if(!getSecretKey().getAlgorithm().equals("AES")) throw new IllegalArgumentException("Invalid algorithm");
			if(this.encryptionCipher != null || this.decryptionCipher != null) throw new IllegalArgumentException("Ecryption already enbled");
			
			try {
				(this.encryptionCipher = Cipher.getInstance("AES/CFB8/NoPadding")).init(Cipher.ENCRYPT_MODE, getSecretKey(), new IvParameterSpec(getSecretKey().getEncoded()));
				(this.decryptionCipher = Cipher.getInstance("AES/CFB8/NoPadding")).init(Cipher.DECRYPT_MODE, getSecretKey(), new IvParameterSpec(getSecretKey().getEncoded()));
				
				this.channel.pipeline().addBefore("prepender", "encrypt", new TcpPacketEncryptor(this.encryptionCipher));
				this.channel.pipeline().addBefore("splitter", "decrypt", new TcpPacketEncryptor(this.decryptionCipher));
			} catch(Exception ex) {
				
			}
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
