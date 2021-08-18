package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;

public class TickSyncPacket extends BedrockPacket {
	
	public long requestTimestamp;
	public long responseTimestamp;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeLong(this.requestTimestamp);
		buf.writeLong(this.responseTimestamp);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.requestTimestamp = buf.readLong();
		this.responseTimestamp = buf.readLong();
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
		return 0x17;
	}

}
