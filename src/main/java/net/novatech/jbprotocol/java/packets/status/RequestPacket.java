package net.novatech.jbprotocol.java.packets.status;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;

public class RequestPacket extends JavaPacket {

	@Override
	public void write(ByteBuf buf) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isServerBound() {
		return true;
	}

	@Override
	public boolean isClientBound() {
		return false;
	}

	@Override
	public byte getId() {
		return 0x00;
	}

}
