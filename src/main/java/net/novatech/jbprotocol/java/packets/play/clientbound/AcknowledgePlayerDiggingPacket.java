package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.Vector3i;

public class AcknowledgePlayerDiggingPacket extends JavaPacket {
	
	public enum DiggingStatus{
		STARTED,
		CANCELLED,
		FINISHED;
	}
	
	public Vector3i location;
	public int block;
	public DiggingStatus status;
	public boolean successful;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		PacketHelper.writePosition(buf, this.location);
		ByteBufUtils.writeUnsignedVarInt(buf, this.block);
		ByteBufUtils.writeUnsignedVarInt(buf, this.status.ordinal());
		buf.writeBoolean(this.successful);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.location = PacketHelper.readPosition(buf);
		this.block = ByteBufUtils.readUnsignedVarInt(buf);
		this.status = DiggingStatus.values()[ByteBufUtils.readUnsignedVarInt(buf)];
		this.successful = buf.readBoolean();
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
		return 0x08;
	}

}
