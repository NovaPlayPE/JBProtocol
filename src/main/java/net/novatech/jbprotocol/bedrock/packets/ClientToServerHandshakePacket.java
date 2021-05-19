package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;

public class ClientToServerHandshakePacket extends BedrockPacket {

	@Override
	public void write(ByteBuf buf) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		// TODO Auto-generated method stub

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
		return 0x04;
	}

}
