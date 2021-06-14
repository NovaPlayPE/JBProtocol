package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.math.Vector3i;
import net.novatech.library.nbt.NBTIO;
import net.novatech.library.nbt.NBTStream;
import net.novatech.library.nbt.tags.CompoundTag;

public class BlockEntityDataPacket extends JavaPacket {
	
	public Vector3i location;
	public byte action;
	public CompoundTag nbt;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		PacketHelper.writePosition(buf, this.location);
		buf.writeByte(this.action);
		NBTIO.writeTag(buf, this.nbt, NBTStream.BIG_ENDIAN);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.location = PacketHelper.readPosition(buf);
		this.action = (byte) buf.readUnsignedByte();
		this.nbt = (CompoundTag) NBTIO.readTag(buf, NBTStream.BIG_ENDIAN);
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
		return 0x0A;
	}

}
