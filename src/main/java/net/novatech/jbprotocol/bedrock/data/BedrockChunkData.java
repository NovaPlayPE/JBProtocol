package net.novatech.jbprotocol.bedrock.data;

import java.util.ArrayList;

import net.novatech.jbprotocol.util.chunk.ChunkData;
import net.novatech.library.nbt.tags.CompoundTag;

public class BedrockChunkData extends ChunkData {
	
	public int subChunkCount;
	public boolean cacheEnabled;
	public ArrayList<Integer> usedBlobHashes;
	
	//payload
	public byte[] bedrockBiomes;
	public CompoundTag[] blockEntities;
	
}