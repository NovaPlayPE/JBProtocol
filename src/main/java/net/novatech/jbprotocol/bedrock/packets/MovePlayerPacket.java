package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.Rotation;
import net.novatech.library.math.Vector3f;

public class MovePlayerPacket extends BedrockPacket {
	
	public enum Mode {
		NORMAL, RESET, TELEPORT, ROTATION;
	}
	
	public enum TeleportationCause {
		UNKNOWN, PROJECTILE, CHORUS, COMMAND, BEHAVIOR;
	}
	
	public long runtimeId;
	public Vector3f position;
	public Rotation rotation;
	public Mode mode;
	public boolean onGround;
	public float ridingEntityRuntimeId;
	public TeleportationCause cause;
	public byte entityType;
	
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarLong(buf, this.runtimeId);
		PacketHelper.writeVector3f(buf, this.position);
		PacketHelper.writeRotation2(buf, this.rotation);
		buf.writeByte(this.mode.ordinal());
		buf.writeBoolean(this.onGround);
		buf.writeFloat(this.ridingEntityRuntimeId);
		if(this.mode == Mode.TELEPORT) {
			ByteBufUtils.writeUnsignedVarLong(buf, this.cause.ordinal());
			buf.writeByte(this.entityType);
		}
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.runtimeId = ByteBufUtils.readUnsignedVarLong(buf);
		this.position = PacketHelper.readVector3f(buf);
		this.rotation = PacketHelper.readRotation2(buf);
		this.mode = Mode.values()[buf.readByte()];
		this.onGround = buf.readBoolean();
		this.ridingEntityRuntimeId = buf.readFloat();
		if(this.mode == Mode.TELEPORT) {
			this.cause = TeleportationCause.values()[ByteBufUtils.readUnsignedVarInt(buf)];
			this.entityType = buf.readByte();
		}
	}

	@Override
	public boolean isServerBound() {
		return true;
	}

	@Override
	public boolean isClientBound() {
		return true;
	}

	@Override
	public byte getId() {
		return 0x13;
	}

}
