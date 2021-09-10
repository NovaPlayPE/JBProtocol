package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.util.chunk.Chunk;

public class ChunkDataPacket extends JavaPacket {
	
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
		return 0x20;
	}

}
