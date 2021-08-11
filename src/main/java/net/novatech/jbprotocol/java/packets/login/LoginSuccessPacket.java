package net.novatech.jbprotocol.java.packets.login;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.io.ByteBufUtils;

public class LoginSuccessPacket extends JavaPacket {

	public UUID uuid;
	public String username;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUUID(buf, this.uuid);
		ByteBufUtils.writeString(buf, this.username);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.uuid = ByteBufUtils.readUUID(buf);
		this.username = ByteBufUtils.readString(buf);
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
		return 0x02;
	}

}
