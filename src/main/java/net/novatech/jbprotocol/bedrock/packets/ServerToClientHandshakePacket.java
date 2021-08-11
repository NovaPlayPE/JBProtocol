package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.library.io.ByteBufUtils;

public class ServerToClientHandshakePacket extends BedrockPacket {

	public String JWT;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeString(buf, this.JWT);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.JWT = ByteBufUtils.readString(buf);
	}

	@Override
	public boolean isServerBound() {
		return false;
	}

	@Override
	public boolean isClientBound() {
		return true;
	}

	@Override
	public byte getId() {
		return 0x03;
	}

}
