package net.novatech.jbprotocol.java.packets.login;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.io.ByteBufUtils;

public class EncryptionRequestPacket extends JavaPacket {
	
	public String serverId;
	public byte[] publicKey;
	public byte[] verifyToken;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeString(buf, this.serverId);
		ByteBufUtils.writeByteArray(buf, this.publicKey);
		ByteBufUtils.writeByteArray(buf, this.verifyToken);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.serverId = ByteBufUtils.readString(buf);
		this.publicKey = ByteBufUtils.readByteArray(buf);
		this.verifyToken = ByteBufUtils.readByteArray(buf);
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
