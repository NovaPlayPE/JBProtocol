package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.util.Chunk;

public class LevelChunkPacket extends BedrockPacket {
	
	public Chunk chunk;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		this.chunk.write(buf);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.chunk.read(buf);
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
		return 0x3A;
	}

}
