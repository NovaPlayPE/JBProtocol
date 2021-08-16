package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.library.io.ByteBufUtils;

public class RiderJumpPacket extends BedrockPacket {
	
	public int jumpStrength;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeSignedVarInt(buf, this.jumpStrength);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.jumpStrength = ByteBufUtils.readSignedVarInt(buf);
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
		return 0x14;
	}

}
