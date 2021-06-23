package net.novatech.jbprotocol.java.packets.play.clientbound;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.utils.ByteBufUtils;

public class BossBarPacket extends JavaPacket {

	public enum Action{
		ADD, REMOVE, UPDATE_HEALTH, UPDATE_TITLE, UPDATE_STYLE, UPDATE_FLAGS;
	}
	
	public enum Color{
		PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE;
	}
	
	public enum Division{
		NO, SIX, TEN, TWELVE, TWENTY;
	}
	
	public UUID uuid;
	public Action action;
	
	public String title;
	public float health;
	public Color color;
	public Division divisions;
	public byte flags;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUUID(buf, this.uuid);
		ByteBufUtils.writeUnsignedVarInt(buf, this.action.ordinal());
		switch(this.action) {
		case ADD:
			ByteBufUtils.writeString(buf, this.title);
			buf.writeFloat(this.health);
			ByteBufUtils.writeUnsignedVarInt(buf, this.color.ordinal());
			ByteBufUtils.writeUnsignedVarInt(buf, this.divisions.ordinal());
			buf.writeByte(this.flags);
			break;
		case REMOVE:
			break;
		case UPDATE_HEALTH:
			buf.writeFloat(this.health);
			break;
		case UPDATE_TITLE:
			ByteBufUtils.writeString(buf, this.title);
			break;
		case UPDATE_STYLE:
			ByteBufUtils.writeUnsignedVarInt(buf, this.color.ordinal());
			ByteBufUtils.writeUnsignedVarInt(buf, this.divisions.ordinal());
			break;
		case UPDATE_FLAGS:
			buf.writeByte(this.flags);
			break;
		}
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.uuid = ByteBufUtils.readUUID(buf);
		this.action = Action.values()[ByteBufUtils.readUnsignedVarInt(buf)];
		switch(this.action) {
		case ADD:
			this.title = ByteBufUtils.readString(buf);
			this.health = buf.readFloat();
			this.color = Color.values()[ByteBufUtils.readUnsignedVarInt(buf)];
			this.divisions = Division.values()[ByteBufUtils.readUnsignedVarInt(buf)];
			this.flags = (byte) buf.readUnsignedByte();
			break;
		case REMOVE:
			break;
		case UPDATE_HEALTH:
			this.health = buf.readFloat();
			break;
		case UPDATE_TITLE:
			this.title = ByteBufUtils.readString(buf);
			break;
		case UPDATE_STYLE:
			this.color = Color.values()[ByteBufUtils.readUnsignedVarInt(buf)];
			this.divisions = Division.values()[ByteBufUtils.readUnsignedVarInt(buf)];
			break;
		case UPDATE_FLAGS:
			this.flags = (byte) buf.readUnsignedByte();
			break;
		}
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
		return 0x0D;
	}

}
