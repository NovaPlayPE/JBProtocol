package net.novatech.jbprotocol.bedrock.packets;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.Rotation;
import net.novatech.library.math.Vector3f;

public class AddPlayerPacket extends BedrockPacket {
	
	public UUID uuid;
	public String username;
	public long uniqueId;
	public long runtimeId;
	public String platformChatID;
	public Vector3f position;
	public Vector3f motion;
	public Rotation rotation;
	public float headYaw = 0f;
	public Object heldItem;
	public Object metadata;
	public int flags;
	public int commandPermissions;
	public int actionPermissions;
	public int permissionLevel;
	public int customStoredPermission;
	public long userId;
	public int links = 0;
	public String deviceId;
	public int deviceOS;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUUID(buf, this.uuid);
		ByteBufUtils.writeString(buf, this.username);
		ByteBufUtils.writeSignedVarLong(buf, this.uniqueId);
		ByteBufUtils.writeUnsignedVarLong(buf, this.runtimeId);
		ByteBufUtils.writeString(buf, this.platformChatID);
		PacketHelper.writeVector3f(buf, this.position);
		PacketHelper.writeVector3f(buf, this.motion);
		PacketHelper.writeRotation2(buf, this.rotation);
		buf.writeFloat(this.headYaw);
		//item
		//metadata
		ByteBufUtils.writeUnsignedVarInt(buf, this.flags);
		ByteBufUtils.writeUnsignedVarInt(buf, this.commandPermissions);
		ByteBufUtils.writeUnsignedVarInt(buf, this.actionPermissions);
		ByteBufUtils.writeUnsignedVarInt(buf, this.permissionLevel);
		ByteBufUtils.writeUnsignedVarInt(buf, this.customStoredPermission);
		buf.writeLong(this.userId);
		ByteBufUtils.writeUnsignedVarInt(buf, this.links);
		ByteBufUtils.writeString(buf, this.deviceId);
		buf.writeInt(this.deviceOS);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.uuid = ByteBufUtils.readUUID(buf);
		this.username = ByteBufUtils.readString(buf);
		this.uniqueId = ByteBufUtils.readSignedVarLong(buf);
		this.runtimeId = ByteBufUtils.readUnsignedVarInt(buf);
		this.platformChatID = ByteBufUtils.readString(buf);
		this.position = PacketHelper.readVector3f(buf);
		this.motion = PacketHelper.readVector3f(buf);
		this.rotation = PacketHelper.readRotation2(buf);
		this.headYaw = buf.readFloat();
		//item
		//metadata
		this.flags = ByteBufUtils.readUnsignedVarInt(buf);
		this.commandPermissions = ByteBufUtils.readUnsignedVarInt(buf);
		this.actionPermissions = ByteBufUtils.readUnsignedVarInt(buf);
		this.permissionLevel = ByteBufUtils.readUnsignedVarInt(buf);
		this.customStoredPermission = ByteBufUtils.readUnsignedVarInt(buf);
		this.userId = buf.readLong();
		this.links = ByteBufUtils.readUnsignedVarInt(buf);
		this.deviceId = ByteBufUtils.readString(buf);
		this.deviceOS = buf.readInt();
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
		return 0x0C;
	}

}
