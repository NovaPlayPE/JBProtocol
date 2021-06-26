package net.novatech.jbprotocol.java.packets.play.clientbound;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.utils.ByteBufUtils;

public class ClientChatPacket extends JavaPacket {

	public enum JavaChatType {

		CHAT((byte) 0),
		SYSTEM_MESSAGE((byte) 1),
		GAME_INFO((byte) 2);

		JavaChatType(byte type) {
			this.type = type;
		}

		private byte type;

		public byte getType() {
			return this.type;
		}

		public static JavaChatType searchByType(byte type) {
			for (JavaChatType types : JavaChatType.values()) {
				if (types.getType() == type) {
					return types;
				}
			}
			return null;
		}
	}

	public String message;
	public JavaChatType type;
	public UUID sender;

	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeString(buf, this.message);
		buf.writeByte(this.type.getType());
		ByteBufUtils.writeUUID(buf, this.sender);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.message = ByteBufUtils.readString(buf);
		this.type = JavaChatType.searchByType(buf.readByte());
		this.sender = ByteBufUtils.readUUID(buf);
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
		return 0x0F;
	}

}
