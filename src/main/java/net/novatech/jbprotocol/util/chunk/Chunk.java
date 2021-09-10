package net.novatech.jbprotocol.util.chunk;

import io.netty.buffer.ByteBuf;

public abstract class Chunk {
	
	public int chunkX;
	public int chunkZ;
	public ChunkData chunkData;
	
	public abstract void write(ByteBuf buf) throws Exception;
	public abstract void read(ByteBuf buf) throws Exception;
	
}