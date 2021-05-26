package net.novatech.jbprotocol.java.packets.play.clientbound;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.math.Rotation;
import net.novatech.library.math.Vector3d;
import net.novatech.library.math.Vector3f;
import net.novatech.library.utils.ByteBufUtils;

public class SpawnEntityPacket extends JavaPacket {
	
	public int id;
	public UUID uuid;
	public int type;
	public Vector3d position;
	public Rotation rotation;
	public int data;
	public Vector3f motion;

	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.id);
		ByteBufUtils.writeUUID(buf, this.uuid);
		ByteBufUtils.writeUnsignedVarInt(buf, this.type);
		PacketHelper.writeVector3d(buf, this.position);
		PacketHelper.writeRotation2(buf, this.rotation);
		buf.writeInt(this.data);
		PacketHelper.writeVector3f(buf, this.motion);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.id = ByteBufUtils.readUnsignedVarInt(buf);
		this.uuid = ByteBufUtils.readUUID(buf);
		this.type = ByteBufUtils.readUnsignedVarInt(buf);
		this.position = PacketHelper.readVector3d(buf);
		this.rotation = PacketHelper.readRotation2(buf);
		this.data = buf.readInt();
		this.motion = PacketHelper.readVector3f(buf);
	}

	@Override
	public boolean isServerBound() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClientBound() {
		return true;
	}

	@Override
	public byte getId() {
		return 0x00;
	}

}
