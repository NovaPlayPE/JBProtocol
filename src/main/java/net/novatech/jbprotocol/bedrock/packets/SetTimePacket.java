package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.library.io.ByteBufUtils;

public class SetTimePacket extends BedrockPacket {
	
	public int time;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeSignedVarInt(buf, this.time);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.time = ByteBufUtils.readSignedVarInt(buf);
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
		return 0x0A;
	}

}
