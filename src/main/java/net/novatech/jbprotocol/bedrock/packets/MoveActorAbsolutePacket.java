package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.BitsUtils;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.Rotation;
import net.novatech.library.math.Vector3f;

public class MoveActorAbsolutePacket extends BedrockPacket {
	
	public long runtimeId;
	public byte flags;
	public Vector3f position;
	public Rotation rotation;
	public boolean onGround;
	public boolean teleported;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarLong(buf, this.runtimeId);
		if(this.onGround) {
			this.flags = BitsUtils.addValue(this.flags, (byte)0x01);
		}
		if(this.teleported) {
			this.flags = BitsUtils.addValue(this.flags, (byte)0x02);
		}
		buf.writeByte(this.flags);
		PacketHelper.writeVector3f(buf, this.position);
		PacketHelper.writeRotation2(buf, this.rotation);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.runtimeId = ByteBufUtils.readUnsignedVarInt(buf);
		this.flags = buf.readByte();
		this.onGround = BitsUtils.checkValue(this.flags, (byte)0x01);
		this.teleported = BitsUtils.checkValue(this.flags, (byte)0x02);
		this.position = PacketHelper.readVector3f(buf);
		this.rotation = PacketHelper.readRotation2(buf);
	}

	@Override
	public boolean isServerBound() {
		return true;
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
