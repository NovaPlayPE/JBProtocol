package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;

import net.novatech.library.math.Vector3f;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;

public class AddPaintingPacket extends BedrockPacket {
	
	public long entityUniqueId;
	public long entityRuntimeId;
	public Vector3f position;
	public int direction;
	public String name;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeSignedVarLong(buf, this.entityUniqueId);
		ByteBufUtils.writeUnsignedVarLong(buf, this.entityRuntimeId);
		PacketHelper.writeVector3f(buf, this.position);
		ByteBufUtils.writeSignedVarInt(buf, this.direction);
		ByteBufUtils.writeString(buf, this.name);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.entityUniqueId = ByteBufUtils.readSignedVarLong(buf);
		this.entityRuntimeId = ByteBufUtils.readUnsignedVarLong(buf);
		this.position = PacketHelper.readVector3f(buf);
		this.direction = ByteBufUtils.readSignedVarInt(buf);
		this.name = ByteBufUtils.readString(buf);
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
		return 0x16;
	}

}
