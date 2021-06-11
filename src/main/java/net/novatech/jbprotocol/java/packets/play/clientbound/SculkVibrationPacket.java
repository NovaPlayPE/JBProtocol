package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.math.Vector3i;
import net.novatech.library.utils.ByteBufUtils;

public class SculkVibrationPacket extends JavaPacket {
	
	public Vector3i sourcePosition;
	public String identifier;
	public int id;
	public Vector3i block;
	public int arrivalTicks;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		PacketHelper.writePosition(buf, this.sourcePosition);
		ByteBufUtils.writeString(buf, this.identifier);
		switch(this.identifier) {
		case "block":
			PacketHelper.writePosition(buf, this.block);
			break;
		case "entity":
			ByteBufUtils.writeUnsignedVarInt(buf, this.id);
			break;
		}
		ByteBufUtils.writeUnsignedVarInt(buf, this.arrivalTicks);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.sourcePosition = PacketHelper.readPosition(buf);
		this.identifier = ByteBufUtils.readString(buf);
		switch(this.identifier) {
		case "block":
			this.block = PacketHelper.readPosition(buf);
			break;
		case "entity":
			this.id = ByteBufUtils.readUnsignedVarInt(buf);
			break;
		}
		this.arrivalTicks = ByteBufUtils.readUnsignedVarInt(buf);
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
