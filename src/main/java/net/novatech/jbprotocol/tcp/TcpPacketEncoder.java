package net.novatech.jbprotocol.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.novatech.jbprotocol.packet.AbstractPacket;
import net.novatech.library.utils.ByteBufUtils;

public class TcpPacketEncoder extends MessageToByteEncoder<AbstractPacket> {

	@Override
	protected void encode(ChannelHandlerContext ctx, AbstractPacket msg, ByteBuf out) throws Exception {
		try {
			ByteBufUtils.writeUnsignedVarInt(out, msg.getId());
			msg.write(out);
		} catch(Exception ex){
			
		}

	}

}
