package net.novatech.jbprotocol.bedrock.packets;

import net.novatech.library.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.util.List;

public class Wrapper extends BedrockPacket {
	
	public ByteBuf payload;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeBytes(this.payload);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.payload = buf;
	}

	@Override
	public boolean isServerBound() {
		return true;
	}

	@Override
	public boolean isClientBound() {
		return true;
	}

	@Override
	public byte getId() {
		return (byte) 0xFF;
	}

}
