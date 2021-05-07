package net.novatech.protocol.bedrock.packets;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import net.novatech.library.utils.ByteBufUtils;

public class LoginPacket extends BedrockPacket{

	public int protocolVersion;
	public byte[] payload;
	
	public byte getId() {
		return 0x01;
	}
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeInt(protocolVersion);
		ByteBufUtils.writeUnsignedVarInt(buf, payload.length);
		buf.writeBytes(payload);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		protocolVersion = buf.readInt();
		payload = new byte[ByteBufUtils.readUnsignedVarInt(buf)];
		buf.readBytes(payload);
		
	}
}
