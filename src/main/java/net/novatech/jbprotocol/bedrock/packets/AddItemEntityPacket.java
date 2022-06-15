package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.vector.Vector3f;

public class AddItemEntityPacket extends BedrockPacket {
	
	public long uniqueId;
	public long runtimeId;
	public Object item;
	public Vector3f position;
	public Vector3f motion;
	public Object metadata;
	public boolean isFromFishing;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeSignedVarLong(buf, this.uniqueId);
		ByteBufUtils.writeUnsignedVarLong(buf, this.runtimeId);
		//item
		PacketHelper.writeVector3f(buf, this.position);
		PacketHelper.writeVector3f(buf, this.motion);
		//metadata
		buf.writeBoolean(this.isFromFishing);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.uniqueId = ByteBufUtils.readSignedVarLong(buf);
		this.runtimeId = ByteBufUtils.readUnsignedVarLong(buf);
		//item;
		this.position = PacketHelper.readVector3f(buf);
		this.motion = PacketHelper.readVector3f(buf);
		//metadata
		this.isFromFishing = buf.readBoolean();
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
		return 0x0F;
	}

}
