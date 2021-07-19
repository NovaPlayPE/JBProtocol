package net.novatech.jbprotocol.tcp;

import java.util.List;
import java.util.zip.Inflater;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import net.novatech.library.utils.ByteBufUtils;

public class TcpPacketDecompressor extends ByteToMessageDecoder {
	
	private TcpSession session;
	private Inflater inflater;
	private byte buf[] = new byte[8192];
	private static int MAX_SIZE = 2097152;
	
	public TcpPacketDecompressor(TcpSession session) {
		this.session = session;
		this.inflater = new Inflater();
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		if(msg.readableBytes() != 0) {
			int size = ByteBufUtils.readUnsignedVarInt(msg);
			if(size == 0) {
				out.add(msg.readBytes(msg.readableBytes()));
			} else {
				if(size < this.session.getCompressionTreshold()) {
					throw new DecoderException("Bad compressed packet: expected to be " + this.session.getCompressionTreshold() + ", but received " + size);
				} if(size > MAX_SIZE) {
					throw new DecoderException("Bad compressed packet: maximum is " + MAX_SIZE + ", but received " + size);
				}
				
				byte[] bytes = new byte[msg.readableBytes()];
				msg.readBytes(bytes, 0, bytes.length);
				this.inflater.setInput(bytes);
				byte[] inflated = new byte[size];
				this.inflater.inflate(inflated);
				out.add(Unpooled.wrappedBuffer(inflated));
				this.inflater.reset();
			}
		}
	}

}
