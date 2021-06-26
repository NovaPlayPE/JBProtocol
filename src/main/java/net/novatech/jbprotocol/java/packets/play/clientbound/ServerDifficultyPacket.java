package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;

public class ServerDifficultyPacket extends JavaPacket {
	
	public enum Difficulty{
		PEACEFUL, EASY, NORMAL, HARD;
	}
	
	public Difficulty difficulty;
	public boolean difficultyLocked;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeByte(this.difficulty.ordinal());
		buf.writeBoolean(this.difficultyLocked);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.difficulty = Difficulty.values()[buf.readUnsignedByte()];
		this.difficultyLocked = buf.readBoolean();
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
		return 0x0E;
	}

}
