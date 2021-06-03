package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.utils.ByteBufUtils;

public class ClientEntityAnimationPacket extends JavaPacket {
	
	public int id;
	public byte animationId;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.id);
		buf.writeByte(this.animationId);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.id = ByteBufUtils.readUnsignedVarInt(buf);
		this.animationId = (byte) buf.readUnsignedByte();
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
		return 0x05;
	}

}
