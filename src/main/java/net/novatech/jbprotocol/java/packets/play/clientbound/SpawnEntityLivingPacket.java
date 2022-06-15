package net.novatech.jbprotocol.java.packets.play.clientbound;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.motion.Rotation;
import net.novatech.library.math.vector.Vector3d;
import net.novatech.library.math.vector.Vector3f;

public class SpawnEntityLivingPacket extends JavaPacket {

	public int id;
	public UUID uuid;
	public int type;
	public Vector3d position;
	public Rotation rotation;
	public float headYaw;
	public Vector3f motion;

	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.id);
		ByteBufUtils.writeUUID(buf, this.uuid);
		ByteBufUtils.writeUnsignedVarInt(buf, this.type);
		PacketHelper.writeVector3d(buf, this.position);
		PacketHelper.writeRotation2(buf, this.rotation);
		buf.writeFloat(this.headYaw);
		PacketHelper.writeVector3f(buf, this.motion);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.id = ByteBufUtils.readUnsignedVarInt(buf);
		this.uuid = ByteBufUtils.readUUID(buf);
		this.type = ByteBufUtils.readUnsignedVarInt(buf);
		this.position = PacketHelper.readVector3d(buf);
		this.rotation = PacketHelper.readRotation2(buf);
		this.headYaw = buf.readFloat();
		this.motion = PacketHelper.readVector3f(buf);
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
		return 0x02;
	}

}
