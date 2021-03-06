package net.novatech.jbprotocol.tcp;

import java.util.List;

import javax.crypto.Cipher;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.Getter;

public class TcpPacketEncryptor extends MessageToMessageEncoder<ByteBuf>{
	
	@Getter
	private Cipher cipher;
	
	public TcpPacketEncryptor(Cipher cipher) {
		this.cipher = cipher;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		this.cipher.update(
				msg.retain().internalNioBuffer(msg.readerIndex(), msg.readableBytes()),
				msg.internalNioBuffer(msg.readerIndex(), msg.readableBytes()).slice()
			);
		out.add(msg);
	}
	
	
	
}