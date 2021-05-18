package net.novatech.jbprotocol.java.packets.login;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.utils.ByteBufUtils;

public class LoginPluginResponsePacket extends JavaPacket {

	public int messageId;
	public byte[] payload;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.messageId);
		if(this.payload != null) {
			buf.writeBoolean(true);
			ByteBufUtils.writeUnsignedVarInt(buf, this.payload);
			buf.writeBytes(this.payload);
		} else {
			buf.writeBoolean(false);
		}
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.messageId = ByteBufUtils.readUnsignedVarInt(buf);
		if(buf.readBoolean()) {
			this.payload = new byte[ByteBufUtils.readUnsignedVarInt(buf)];
			buf.readBytes(this.payload);
		}
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
		return 0x02;
	}

}
