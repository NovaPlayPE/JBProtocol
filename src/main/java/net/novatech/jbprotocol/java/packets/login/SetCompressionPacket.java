package net.novatech.jbprotocol.java.packets.login;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.io.ByteBufUtils;

public class SetCompressionPacket extends JavaPacket {

	public int treshold;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.treshold);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.treshold = ByteBufUtils.readUnsignedVarInt(buf);
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
		return 0x03;
	}

}
