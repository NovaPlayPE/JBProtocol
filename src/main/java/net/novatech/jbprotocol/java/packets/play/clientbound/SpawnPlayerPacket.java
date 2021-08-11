package net.novatech.jbprotocol.java.packets.play.clientbound;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.Rotation;
import net.novatech.library.math.Vector3d;

public class SpawnPlayerPacket extends JavaPacket {
	
	public int id;
	public UUID uuid;
	public Vector3d position;
	public Rotation rotation;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.id);
		ByteBufUtils.writeUUID(buf, this.uuid);
		PacketHelper.writeVector3d(buf, this.position);
		PacketHelper.writeRotation2(buf, this.rotation);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.id = ByteBufUtils.readUnsignedVarInt(buf);
		this.uuid = ByteBufUtils.readUUID(buf);
		this.position = PacketHelper.readVector3d(buf);
		this.rotation = PacketHelper.readRotation2(buf);
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
		return 0x04;
	}

}
