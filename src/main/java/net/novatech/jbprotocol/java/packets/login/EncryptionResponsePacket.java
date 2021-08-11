package net.novatech.jbprotocol.java.packets.login;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.io.ByteBufUtils;

public class EncryptionResponsePacket extends JavaPacket {

	public byte[] sharedKey;
	public byte[] verifyToken;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeByteArray(buf, this.sharedKey);
		ByteBufUtils.writeByteArray(buf, this.verifyToken);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.sharedKey = ByteBufUtils.readByteArray(buf);
		this.verifyToken = ByteBufUtils.readByteArray(buf);
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
		return 0x01;
	}

}
