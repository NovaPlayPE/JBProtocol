package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.math.Vector3d;
import net.novatech.library.utils.ByteBufUtils;

public class SpawnOrbExperiencePacket extends JavaPacket {
	
	public int id;
	public Vector3d position;
	public short count;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.id);
		PacketHelper.writeVector3d(buf, this.position);
		buf.writeShort(this.count);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.id = ByteBufUtils.readUnsignedVarInt(buf);
		this.position = PacketHelper.readVector3d(buf);
		this.count = buf.readShort();
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
		return 0x01;
	}

}
