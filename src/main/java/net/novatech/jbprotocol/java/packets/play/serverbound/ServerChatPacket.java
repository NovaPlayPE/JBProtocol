package net.novatech.jbprotocol.java.packets.play.serverbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.io.ByteBufUtils;

public class ServerChatPacket extends JavaPacket {

	public String message = "";
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeString(buf, this.message);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.message = ByteBufUtils.readString(buf);
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
		return 0x03;
	}

}
