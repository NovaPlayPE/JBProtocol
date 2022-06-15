package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.vector.Vector3i;

public class UpdateBlockPacket extends BedrockPacket {
	
	public Vector3i coordinates;
	public int runtimeId;
	public int flags;
	public int layer;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		PacketHelper.writeVector3i(buf, this.coordinates);
		ByteBufUtils.writeUnsignedVarInt(buf, this.runtimeId);
		ByteBufUtils.writeUnsignedVarInt(buf, this.flags);
		ByteBufUtils.writeUnsignedVarInt(buf, this.layer);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.coordinates = PacketHelper.readVector3i(buf);
		this.runtimeId = ByteBufUtils.readUnsignedVarInt(buf);
		this.flags = ByteBufUtils.readUnsignedVarInt(buf);
		this.layer = ByteBufUtils.readUnsignedVarInt(buf);
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
		return 0x15;
	}

}
