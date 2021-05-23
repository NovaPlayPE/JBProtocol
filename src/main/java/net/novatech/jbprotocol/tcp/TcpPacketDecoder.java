package net.novatech.jbprotocol.tcp;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.novatech.library.utils.ByteBufUtils;

public class TcpPacketDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(!ctx.channel().isActive()) return;
		out.add(in);
	}

}
