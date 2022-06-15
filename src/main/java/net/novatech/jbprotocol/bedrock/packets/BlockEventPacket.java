package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.vector.Vector3i;

public class BlockEventPacket extends BedrockPacket {
	
	public Vector3i position;
	public int eventType;
	public int eventData;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		PacketHelper.writeVector3i(buf, this.position);
		ByteBufUtils.writeSignedVarInt(buf, this.eventType);
		ByteBufUtils.writeSignedVarInt(buf, this.eventData);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.position = PacketHelper.readVector3i(buf);
		this.eventType = ByteBufUtils.readSignedVarInt(buf);
		this.eventData = ByteBufUtils.readSignedVarInt(buf);
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
		return 0x1A;
	}

}
