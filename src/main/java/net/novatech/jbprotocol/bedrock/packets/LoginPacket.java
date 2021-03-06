package net.novatech.jbprotocol.bedrock.packets;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import net.novatech.library.io.ByteBufUtils;

public class LoginPacket extends BedrockPacket{

	public int protocolVersion;
	public byte[] payload;
	
	public byte getId() {
		return 0x01;
	}
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeInt(this.protocolVersion);
		ByteBufUtils.writeByteArray(buf, this.payload);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.protocolVersion = buf.readInt();
		this.payload = ByteBufUtils.readByteArray(buf);
		
	}

	@Override
	public boolean isServerBound() {
		return true;
	}

	@Override
	public boolean isClientBound() {
		return false;
	}
}
