package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.vector.Vector3i;

public class BlockChangePacket extends JavaPacket {
	
	public Vector3i location;
	public int blockId;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		PacketHelper.writePosition(buf, this.location);
		ByteBufUtils.writeUnsignedVarInt(buf, this.blockId);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.location = PacketHelper.readPosition(buf);
		this.blockId = ByteBufUtils.readUnsignedVarInt(buf);
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
