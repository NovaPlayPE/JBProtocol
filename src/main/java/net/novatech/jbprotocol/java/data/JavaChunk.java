package net.novatech.jbprotocol.java.data;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.util.Chunk;
import net.novatech.library.nbt.NBTIO;
import net.novatech.library.nbt.NBTStream;
import net.novatech.library.nbt.tags.CompoundTag;
import net.novatech.library.utils.ByteBufUtils;

public class JavaChunk extends Chunk{

	@Override
	public void write(ByteBuf buf) throws Exception {
		JavaChunkData data = (JavaChunkData) this.chunkData;
		buf.writeInt(this.chunkX);
		buf.writeInt(this.chunkZ);
		buf.writeBoolean(data.fullChunk);
		ByteBufUtils.writeUnsignedVarInt(buf, data.bitMapMask);
		NBTIO.writeTag(buf, data.heightMaps, NBTStream.BIG_ENDIAN);
		ByteBufUtils.writeUnsignedVarInt(buf, data.biomesLength);
		for(int biome : data.biomes) {
			ByteBufUtils.writeUnsignedVarInt(buf, biome);
		}
		ByteBufUtils.writeByteArray(buf, data.data);
		ByteBufUtils.writeUnsignedVarInt(buf, data.blockEntities.length);
		for(CompoundTag be : data.blockEntities) {
			NBTIO.writeTag(buf, be, NBTStream.BIG_ENDIAN);
		}
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		JavaChunkData data = (JavaChunkData)this.chunkData;
		this.chunkX = buf.readInt();
		this.chunkZ = buf.readInt();
		data.fullChunk = buf.readBoolean();
		data.bitMapMask = ByteBufUtils.readUnsignedVarInt(buf);
		data.heightMaps = (CompoundTag) NBTIO.readTag(buf, NBTStream.BIG_ENDIAN);
		data.biomesLength = ByteBufUtils.readUnsignedVarInt(buf);
		data.biomes = new int[data.biomesLength];
		for(int i = 0; i < data.biomesLength; i++) {
			data.biomes[i] = ByteBufUtils.readUnsignedVarInt(buf);
		}
		data.data = ByteBufUtils.readByteArray(buf);
		int i2 = ByteBufUtils.readUnsignedVarInt(buf);
		data.blockEntities = new CompoundTag[i2];
		for(int num = 0; num < i2; num++) {
			data.blockEntities[num] = (CompoundTag) NBTIO.readTag(buf, NBTStream.BIG_ENDIAN);
		}
	}

}
