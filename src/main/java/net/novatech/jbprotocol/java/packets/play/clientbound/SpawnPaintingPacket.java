package net.novatech.jbprotocol.java.packets.play.clientbound;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.math.Vector3i;
import net.novatech.library.utils.ByteBufUtils;

public class SpawnPaintingPacket extends JavaPacket {
	
	public int id;
	public UUID uuid;
	public int motive;
	public Vector3i position;
	public byte direction;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.id);
		ByteBufUtils.writeUUID(buf, this.uuid);
		ByteBufUtils.writeUnsignedVarInt(buf, this.motive);
		
		long x = position.getX() & 0x3FFFFFF;
		long y = position.getY() & 0xFFF;
		long z = position.getZ() & 0x3FFFFFF;
		
		buf.writeLong(x << 38 | z << 12 | y);
		buf.writeByte(this.direction);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.id = ByteBufUtils.readUnsignedVarInt(buf);
		this.uuid = ByteBufUtils.readUUID(buf);
		this.motive = ByteBufUtils.readUnsignedVarInt(buf);
		
		long value = buf.readLong();
		int x = (int) (value >> 38);
		int y = (int) (value & 0xFFF);
		int z = (int) (value << 26 >> 38);
		
		this.position = new Vector3i(x,y,z);
		this.direction = buf.readByte();
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
		return 0x03;
	}

}
