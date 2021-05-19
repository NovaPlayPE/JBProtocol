package net.novatech.jbprotocol.java.packets.login;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.utils.ByteBufUtils;

public class DissconnectPacket extends JavaPacket {

	public String reason;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeString(buf, this.reason);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.reason = ByteBufUtils.readString(buf);
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
		return 0x00;
	}

}
