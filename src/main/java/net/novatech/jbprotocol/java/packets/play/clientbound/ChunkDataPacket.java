package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.nbt.NBTIO;
import net.novatech.library.nbt.NBTStream;
import net.novatech.library.nbt.tags.CompoundTag;
import net.novatech.library.utils.ByteBufUtils;

public class ChunkDataPacket extends JavaPacket {
	
	public int chunkX;
	public int chunkZ;
	public boolean fullChunk;
	public int bitMapMask;
	public CompoundTag heightMaps;
	public int biomesLength;
	public int[] biomes;
	public byte[] data;
	public CompoundTag[] blockEntities;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeInt(this.chunkX);
		buf.writeInt(this.chunkZ);
		buf.writeBoolean(this.fullChunk);
		ByteBufUtils.writeUnsignedVarInt(buf, this.bitMapMask);
		NBTIO.writeTag(buf, this.heightMaps, NBTStream.BIG_ENDIAN);
		ByteBufUtils.writeUnsignedVarInt(buf, this.biomesLength);
		for(int biome : biomes) {
			ByteBufUtils.writeUnsignedVarInt(buf, biome);
		}
		ByteBufUtils.writeByteArray(buf, this.data);
		ByteBufUtils.writeUnsignedVarInt(buf, this.blockEntities.length);
		for(CompoundTag be : blockEntities) {
			NBTIO.writeTag(buf, be, NBTStream.BIG_ENDIAN);
		}
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.chunkX = buf.readInt();
		this.chunkZ = buf.readInt();
		this.fullChunk = buf.readBoolean();
		this.bitMapMask = ByteBufUtils.readUnsignedVarInt(buf);
		this.heightMaps = (CompoundTag) NBTIO.readTag(buf, NBTStream.BIG_ENDIAN);
		this.biomesLength = ByteBufUtils.readUnsignedVarInt(buf);
		this.biomes = new int[this.biomesLength];
		for(int i = 0; i < this.biomesLength; i++) {
			this.biomes[i] = ByteBufUtils.readUnsignedVarInt(buf);
		}
		this.data = ByteBufUtils.readByteArray(buf);
		int i2 = ByteBufUtils.readUnsignedVarInt(buf);
		this.blockEntities = new CompoundTag[i2];
		for(int num = 0; num < i2; num++) {
			this.blockEntities[num] = (CompoundTag) NBTIO.readTag(buf, NBTStream.BIG_ENDIAN);
		}
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
