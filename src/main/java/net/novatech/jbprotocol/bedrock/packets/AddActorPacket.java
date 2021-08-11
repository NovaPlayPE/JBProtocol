package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.Rotation;
import net.novatech.library.math.Vector3f;

public class AddActorPacket extends BedrockPacket {
	
	public long uniqueId;
	public long runtimeId;
	public String type;
	public Vector3f position;
	public Vector3f motion;
	public Rotation rotation;
	//other things are incomplete
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeSignedVarLong(buf, this.uniqueId);
		ByteBufUtils.writeUnsignedVarLong(buf, this.runtimeId);
		ByteBufUtils.writeString(buf, this.type);
		PacketHelper.writeVector3f(buf, this.position);
		PacketHelper.writeVector3f(buf, this.motion);
		PacketHelper.writeRotation2(buf, this.rotation);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.uniqueId = ByteBufUtils.readSignedVarLong(buf);
		this.runtimeId = ByteBufUtils.readUnsignedVarLong(buf);
		this.type = ByteBufUtils.readString(buf);
		this.position = PacketHelper.readVector3f(buf);
		this.motion = PacketHelper.readVector3f(buf);
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
		return 0x0D;
	}

}
