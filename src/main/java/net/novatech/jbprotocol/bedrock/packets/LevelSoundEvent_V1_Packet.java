package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;

import net.novatech.library.math.Vector3f;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;

public class LevelSoundEvent_V1_Packet extends BedrockPacket{
	
	public int soundId;
	public Vector3f position;
	public int blockId;
	public int entityType;
	public boolean isBaby;
	public boolean isGlobal;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeByte(this.soundId);
		PacketHelper.writeVector3f(buf, this.position);
		ByteBufUtils.writeSignedVarInt(buf, this.blockId);
		ByteBufUtils.writeSignedVarInt(buf, this.entityType);
		buf.writeBoolean(this.isBaby);
		buf.writeBoolean(this.isGlobal);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.soundId = buf.readByte();
		this.position = PacketHelper.readVector3f(buf);
		this.blockId = ByteBufUtils.readSignedVarInt(buf);
		this.entityType = ByteBufUtils.readSignedVarInt(buf);
		this.isBaby = buf.readBoolean();
		this.isGlobal = buf.readBoolean();
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
		return 0x18;
	}

}
