package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.math.Vector3i;
import net.novatech.library.utils.ByteBufUtils;

public class BlockActionPacket extends JavaPacket {
	
	public Vector3i location;
	public byte actionId;
	public byte actionParam;
	public int blockType;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		PacketHelper.writePosition(buf, this.location);
		buf.writeByte(this.actionId);
		buf.writeByte(this.actionParam);
		ByteBufUtils.writeUnsignedVarInt(buf, this.blockType);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.location = PacketHelper.readPosition(buf);
		this.actionId = (byte) buf.readUnsignedByte();
		this.actionParam = (byte) buf.readUnsignedByte();
		this.blockType = ByteBufUtils.readUnsignedVarInt(buf);
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
		return 0x0B;
	}

}
