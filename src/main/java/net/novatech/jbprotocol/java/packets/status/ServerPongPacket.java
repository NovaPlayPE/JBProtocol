package net.novatech.jbprotocol.java.packets.status;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;

public class ServerPongPacket extends JavaPacket {

	public long pingTime;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeLong(this.pingTime);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.pingTime = buf.readLong();
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
