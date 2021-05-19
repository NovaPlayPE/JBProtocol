package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.library.utils.ByteBufUtils;

public class DissconnectPacket extends BedrockPacket {

	public boolean hideScreen;
	public String kickMessage;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeBoolean(this.hideScreen);
		ByteBufUtils.writeString(buf, this.kickMessage);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.hideScreen = buf.readBoolean();
		this.kickMessage = ByteBufUtils.readString(buf);
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
		return 0x05;
	}

}
