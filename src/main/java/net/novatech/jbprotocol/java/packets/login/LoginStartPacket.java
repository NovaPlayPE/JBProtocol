package net.novatech.jbprotocol.java.packets.login;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.io.ByteBufUtils;

public class LoginStartPacket extends JavaPacket {
	
	public String username;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeString(buf,  this.username);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.username = ByteBufUtils.readString(buf);
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
