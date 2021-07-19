package net.novatech.jbprotocol.tcp;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TcpPacketSizer extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(ctx.channel().isActive()) {
			in.markReaderIndex();
			int length = 0;
			for(int shift = 0; shift < 24; shift +=7) {
				if(!in.isReadable()) {
					in.resetReaderIndex();	
					return;
				}
				byte by = in.readByte();
				length |= (by & 0x7FL) << shift;
				if(by < 0) continue;
				if(in.readableBytes() < length) {
					in.resetReaderIndex();
					return;
				}
				out.add(in.readBytes(length));
			}
		} else {
			in.skipBytes(in.readableBytes());
		}

	}

}
