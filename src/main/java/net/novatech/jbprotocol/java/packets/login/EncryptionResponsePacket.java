package net.novatech.jbprotocol.java.packets.login;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.utils.ByteBufUtils;

public class EncryptionResponsePacket extends JavaPacket {

	public byte[] sharedKey;
	public byte[] verifyToken;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.sharedKey.length);
		buf.writeBytes(this.sharedKey);
		ByteBufUtils.writeUnsignedVarInt(buf, this.verifyToken.length);
		buf.writeBytes(this.verifyToken);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.sharedKey = new byte[ByteBufUtils.readUnsignedVarInt(buf)];
		buf.readBytes(this.sharedKey);
		this.verifyToken = new byte[ByteBufUtils.readUnsignedVarInt(buf)];
		buf.readBytes(this.verifyToken);
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
