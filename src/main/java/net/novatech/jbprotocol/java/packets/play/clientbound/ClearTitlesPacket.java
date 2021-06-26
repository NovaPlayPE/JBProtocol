package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;

public class ClearTitlesPacket extends JavaPacket {
	
	public boolean reset;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeBoolean(this.reset);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.reset = buf.readBoolean();
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
		return 0x10;
	}

}
