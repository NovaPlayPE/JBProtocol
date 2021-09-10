package net.novatech.jbprotocol.bedrock.data;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.novatech.jbprotocol.util.chunk.Chunk;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.nbt.NBTIO;
import net.novatech.library.nbt.NBTStream;
import net.novatech.library.nbt.tags.CompoundTag;

public class BedrockChunk extends Chunk {

	@Override
	public void write(ByteBuf buf) throws Exception {
		BedrockChunkData data = (BedrockChunkData)this.chunkData;
		
		ByteBufUtils.writeSignedVarInt(buf, this.chunkX);
		ByteBufUtils.writeSignedVarInt(buf, this.chunkZ);
		ByteBufUtils.writeUnsignedVarInt(buf, data.subChunkCount);
		buf.writeBoolean(data.cacheEnabled);
		if(data.cacheEnabled) {
			ByteBufUtils.writeUnsignedVarInt(buf, data.usedBlobHashes.size());
			for(Integer i : data.usedBlobHashes) {
				ByteBufUtils.writeUnsignedVarInt(buf, i);
			}
		}
		
		ByteBuf buffer = Unpooled.buffer(34);
		ByteBufUtils.writeByteArray(buffer, data.bedrockBiomes);
		buffer.writeByte(0);
		ByteBufUtils.writeUnsignedVarInt(buffer, 0);
		ByteBufUtils.writeUnsignedVarInt(buffer, data.blockEntities.length);
		for(CompoundTag nbt : data.blockEntities) {
			NBTIO.writeTag(buffer, nbt, NBTStream.VAR_INT);
		}
		
		byte[] payload = new byte[buffer.writerIndex()];
		buffer.readBytes(payload);
		
		ByteBufUtils.writeByteArray(buf, payload);
		
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		BedrockChunkData data = (BedrockChunkData)this.chunkData;
		
		this.chunkX = ByteBufUtils.readSignedVarInt(buf);
		this.chunkZ = ByteBufUtils.readSignedVarInt(buf);
		data.subChunkCount = ByteBufUtils.readUnsignedVarInt(buf);
		data.cacheEnabled = buf.readBoolean();
		if(data.cacheEnabled) {
			int length = ByteBufUtils.readUnsignedVarInt(buf);
			data.usedBlobHashes = new ArrayList<Integer>();
			for(int num = 0; num < length; num++) {
				data.usedBlobHashes.add(ByteBufUtils.readUnsignedVarInt(buf));
			}
		}
		
		byte[] payload = ByteBufUtils.readByteArray(buf);
		ByteBuf buffer = Unpooled.copiedBuffer(payload);
		data.bedrockBiomes = ByteBufUtils.readByteArray(buffer);
		buf.readByte();
		ByteBufUtils.readUnsignedVarInt(buffer);
		int i = ByteBufUtils.readUnsignedVarInt(buf);
		data.blockEntities = new CompoundTag[i];
		for(int i1 = 0; i1 < i; i1++) {
			data.blockEntities[i1] = (CompoundTag) NBTIO.readTag(buffer, NBTStream.VAR_INT);
		}
	}

}
