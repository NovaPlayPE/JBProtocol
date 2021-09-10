package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;

public class DeclareCommandsPacket extends JavaPacket {

	private static final int FLAG_TYPE_MASK = 0x03;
	private static final int FLAG_EXECUTABLE = 0x04;
	private static final int FLAG_REDIRECT = 0x08;
	private static final int FLAG_SUGGESTION_TYPE = 0x10;
	private static final int NUMBER_FLAG_MIN_DEFINED = 0x01;
	private static final int NUMBER_FLAG_MAX_DEFINED = 0x02;

	private static final int ENTITY_FLAG_SINGLE_TARGET = 0x01;
	private static final int ENTITY_FLAG_PLAYERS_ONLY = 0x02;

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
		return false;
	}

	@Override
	public boolean isClientBound() {
		return true;
	}

	@Override
	public byte getId() {
		return 0x12;
	}

}
