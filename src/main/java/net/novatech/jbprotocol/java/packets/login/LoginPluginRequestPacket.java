package net.novatech.jbprotocol.java.packets.login;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.io.ByteBufUtils;

public class LoginPluginRequestPacket extends JavaPacket {
	
	public int messageId;
	public String channel;
	public byte[] data;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.messageId);
		ByteBufUtils.writeString(buf, this.channel);
		ByteBufUtils.writeByteArray(buf, this.data);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.messageId = ByteBufUtils.readUnsignedVarInt(buf);
		this.channel = ByteBufUtils.readString(buf);
		this.data = ByteBufUtils.readByteArray(buf);
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
		return 0x04;
	}

}
