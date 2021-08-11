package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.library.io.ByteBufUtils;

public class TakeItemEntityPacket extends BedrockPacket {
	
	public long itemRuntimeId;
	public long runtimeId;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarLong(buf, this.itemRuntimeId);
		ByteBufUtils.writeUnsignedVarLong(buf, this.runtimeId);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.itemRuntimeId = ByteBufUtils.readUnsignedVarLong(buf);
		this.runtimeId = ByteBufUtils.readUnsignedVarLong(buf);
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
		return 0x11;
	}

}
