package net.novatech.jbprotocol.java.data;

import net.novatech.jbprotocol.util.ChunkData;
import net.novatech.library.nbt.tags.CompoundTag;

public class JavaChunkData extends ChunkData {
	
	public boolean fullChunk;
	public int bitMapMask;
	public CompoundTag heightMaps;
	public int biomesLength;
	public int[] biomes;
	public byte[] data;
	public CompoundTag[] blockEntities;
	
}