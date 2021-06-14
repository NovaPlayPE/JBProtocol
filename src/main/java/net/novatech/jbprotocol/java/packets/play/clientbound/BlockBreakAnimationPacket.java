package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.math.Vector3i;
import net.novatech.library.utils.ByteBufUtils;

public class BlockBreakAnimationPacket extends JavaPacket {
	
	public int id;
	public Vector3i location;
	public byte destroyStage;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.id);
		PacketHelper.writePosition(buf, this.location);
		buf.writeByte(this.destroyStage);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.id = ByteBufUtils.readUnsignedVarInt(buf);
		this.location = PacketHelper.readPosition(buf);
		this.destroyStage = buf.readByte();
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
		return 0x09;
	}

}
