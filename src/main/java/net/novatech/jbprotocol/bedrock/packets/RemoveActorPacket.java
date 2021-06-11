package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.library.utils.ByteBufUtils;

public class RemoveActorPacket extends BedrockPacket {
	
	public long uniqueId;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeSignedVarLong(buf, this.uniqueId);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.uniqueId = ByteBufUtils.readSignedVarLong(buf);
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
		return 0x0E;
	}

}
