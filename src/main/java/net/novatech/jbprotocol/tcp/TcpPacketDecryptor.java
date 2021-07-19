package net.novatech.jbprotocol.tcp;

import java.util.List;

import javax.crypto.Cipher;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.Getter;

public class TcpPacketDecryptor extends MessageToMessageDecoder<ByteBuf> {

	@Getter
	private Cipher cipher;
	
	public TcpPacketDecryptor(Cipher cipher) {
		this.cipher = cipher;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		this.cipher.update(
				msg.retain().internalNioBuffer(msg.readerIndex(), msg.readableBytes()),
				msg.internalNioBuffer(msg.readerIndex(), msg.readableBytes()).slice()
			);
		out.add(msg);
	}

}
