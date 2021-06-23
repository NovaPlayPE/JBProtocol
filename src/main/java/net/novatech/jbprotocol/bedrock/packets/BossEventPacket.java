package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.library.utils.ByteBufUtils;

public class BossEventPacket extends BedrockPacket {
	
	public enum Type{
		SHOW, REGISTER, HIDE, UNREGISTER, HEALTH, TITLE, UNKNOWN, TEXTURE;
	}
	
	public enum Color{
		PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE;
	}
	
	public enum Overlay{
		NO, SIX, TEN, TWELVE, TWENTY;
	}
	
	public long bossId;
	public Type type;
	
	public long pid;
	public float health;
	public String title;
	public short unknown;
	public Color color;
	public Overlay overlay;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeSignedVarLong(buf, this.bossId);
		ByteBufUtils.writeUnsignedVarInt(buf, this.type.ordinal());
		switch(this.type) {
		case REGISTER:
		case UNREGISTER:
			ByteBufUtils.writeSignedVarLong(buf, this.pid);
			break;
		case SHOW:
			ByteBufUtils.writeString(buf, this.title);
			buf.writeFloat(this.health);
			break;
		case TEXTURE:
			ByteBufUtils.writeUnsignedVarInt(buf, this.color.ordinal());
			ByteBufUtils.writeUnsignedVarInt(buf, this.overlay.ordinal());
			break;
		case TITLE:
			ByteBufUtils.writeString(buf, this.title);
			break;
		case UNKNOWN:
			buf.writeShort(this.unknown);
			break;
		}
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.bossId = ByteBufUtils.readSignedVarLong(buf);
		this.type = Type.values()[ByteBufUtils.readUnsignedVarInt(buf)];
		switch(this.type) {
		case REGISTER:
		case UNREGISTER:
			this.pid = ByteBufUtils.readSignedVarLong(buf);
			break;
		case SHOW:
			this.title = ByteBufUtils.readString(buf);
			this.health = buf.readFloat();
			break;
		case TEXTURE:
			this.color = Color.values()[ByteBufUtils.readUnsignedVarInt(buf)];
			this.overlay = Overlay.values()[ByteBufUtils.readUnsignedVarInt(buf)];
			break;
		case TITLE:
			this.title = ByteBufUtils.readString(buf);
			break;
		case UNKNOWN:
			this.unknown = buf.readShort();
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
		return 0x4A;
	}

}
