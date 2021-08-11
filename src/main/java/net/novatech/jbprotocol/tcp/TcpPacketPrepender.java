package net.novatech.jbprotocol.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.novatech.library.io.ByteBufUtils;

public class TcpPacketPrepender extends MessageToByteEncoder<ByteBuf> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(out, msg.readableBytes());
		out.writeBytes(msg);
	}

}
