package net.novatech.jbprotocol.java.packets.play.clientbound;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.Vector3i;

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
		PacketHelper.writePosition(buf, this.position);
		buf.writeByte(this.direction);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.id = ByteBufUtils.readUnsignedVarInt(buf);
		this.uuid = ByteBufUtils.readUUID(buf);
		this.motive = ByteBufUtils.readUnsignedVarInt(buf);
		this.position = PacketHelper.readPosition(buf);
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
