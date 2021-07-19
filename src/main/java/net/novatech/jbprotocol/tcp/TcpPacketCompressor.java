package net.novatech.jbprotocol.tcp;

import java.util.zip.Deflater;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import net.novatech.library.utils.ByteBufUtils;

public class TcpPacketCompressor extends MessageToByteEncoder<ByteBuf> {
	
	private TcpSession session;
	private Deflater deflater;
	private byte buf[] = new byte[8192];
	
	public TcpPacketCompressor(TcpSession session) {
		this.session = session;
		this.deflater = new Deflater();
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		int readable = msg.readableBytes();
		if(readable < this.session.getCompressionTreshold()) {
			ByteBufUtils.writeUnsignedVarInt(out, 0);
			out.writeBytes(msg);
		} else {
			byte[] bytes = new byte[readable];
			msg.readBytes(bytes);
			ByteBufUtils.writeUnsignedVarInt(out, bytes.length);
			this.deflater.setInput(bytes, 0, readable);
			this.deflater.finish();
			while(!this.deflater.finished()) {
				int length = this.deflater.deflate(this.buf);
				out.writeBytes(this.buf, 0, length);
			}
			
			this.deflater.reset();
		}
	}

}
