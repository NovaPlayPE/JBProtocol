package net.novatech.jbprotocol.packet;

import io.gomint.jraknet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.bedrock.BedrockSession;
import net.novatech.jbprotocol.bedrock.packets.BedrockPacket;
import net.novatech.jbprotocol.bedrock.packets.Wrapper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.motion.Rotation;
import net.novatech.library.math.vector.Vector3d;
import net.novatech.library.math.vector.Vector3f;
import net.novatech.library.math.vector.Vector3i;

public class PacketHelper {
	
	public static void writeBatchPacket(BedrockSession session, BedrockPacket[] packets) {
		ByteBuf buf = convertPacketsToBytes(packets);
		if(buf.readableBytes() == 0) {
			buf.release();
			return;
		}
		Wrapper wrapper = new Wrapper();
		wrapper.payload = session.outputProcess.process(buf);
		session.sendPacketImmediatly(wrapper);
	}
	
	public static void writePosition(ByteBuf buf, Vector3i position) {
		long x = position.getX() & 0x3FFFFFF;
		long y = position.getY() & 0xFFF;
		long z = position.getZ() & 0x3FFFFFF;
		
		buf.writeLong(x << 38 | z << 12 | y);
	}
	
	public static Vector3i readPosition(ByteBuf buf) {
		long value = buf.readLong();
		int x = (int) (value >> 38);
		int y = (int) (value & 0xFFF);
		int z = (int) (value << 26 >> 38);
		
		return new Vector3i(x,y,z);
	}
	
	public static void writeVector3d(ByteBuf buf, Vector3d vec) {
		buf.writeDouble(vec.getX());
		buf.writeDouble(vec.getY());
		buf.writeDouble(vec.getZ());
	}
	
	public static Vector3d readVector3d(ByteBuf buf) {
		double x = buf.readDouble();
		double y = buf.readDouble();
		double z = buf.readDouble();
		return new Vector3d(x, y, z);
	}
	
	public static void writeVector3f(ByteBuf buf, Vector3f vec) {
		buf.writeFloat(vec.getX());
		buf.writeFloat(vec.getY());
		buf.writeFloat(vec.getZ());
	}
	
	public static Vector3f readVector3f(ByteBuf buf) {
		float x = buf.readFloat();
		float y = buf.readFloat();
		float z = buf.readFloat();
		return new Vector3f(x, y, z);
	}
	
	public static void writeVector3i(ByteBuf buf, Vector3i vec) {
		buf.writeInt(vec.getX());
		buf.writeInt(vec.getY());
		buf.writeInt(vec.getZ());
	}
	
	public static Vector3i readVector3i(ByteBuf buf) {
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		return new Vector3i(x, y, z);
	}
	
	public static void writeRotation(ByteBuf buf, Rotation rotation) {
		buf.writeFloat(rotation.getYaw());
		buf.writeFloat(rotation.getPitch());
	}
	
	public static Rotation readRotation(ByteBuf buf) {
		float yaw = buf.readFloat();
		float pitch = buf.readFloat();
		return new Rotation(yaw, pitch);
	}
	
	public static void writeRotation2(ByteBuf buf, Rotation rotation) {
		buf.writeFloat(rotation.getPitch());
		buf.writeFloat(rotation.getYaw());
		buf.writeFloat(rotation.getHeadYaw());
	}
	
	public static Rotation readRotation2(ByteBuf buf) {
		float pitch = buf.readFloat();
		float yaw = buf.readFloat();
		float headYaw = buf.readFloat();
		Rotation rot = new Rotation(yaw, pitch);
		rot.setHeadYaw(headYaw);
		return rot;
	}
	
	private static ByteBuf convertPacketsToBytes(BedrockPacket[] packets) {
		PacketBuffer buffer = new PacketBuffer(packets.length * 5 * (packets.length * 32));
		int pos;
		for(BedrockPacket pk : packets) {
			try {
				int length = buffer.getWritePosition();
				buffer.writeBytes(new byte[] {(byte)0x80, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x0});
				pos = buffer.getWritePosition();
				
				ByteBufUtils.writeUnsignedVarInt(buffer.getBuffer(), pk.getId());
				pk.write(buffer.getBuffer());
				
				int writtenBytes = buffer.getWritePosition() - pos;
				writePrimitiveVarInt(length, writtenBytes, buffer.getBuffer());
			} catch (Exception ex) {}
		}
		return buffer.getBuffer();
	}
	
	private static void  writePrimitiveVarInt(int start, int value, ByteBuf buffer) {
		int current = start;
		while((value & -128) != 0) {
			buffer.setByte(current++, value & 0x7F | 0x80);
			value >>>= 7;
		}
		buffer.setByte(current, value | 0x80);
	}
	
}