package net.novatech.jbprotocol.bedrock.packets;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.novatech.library.utils.ByteBufUtils;

public class TextPacket extends BedrockPacket {
	
	public enum BedrockTextType{
		
		RAW((byte)0),
		CHAT((byte)1),
		TRANSLATION((byte)2),
		POPUP((byte)3),
		JUKEBOX((byte)4),
		TIP((byte)5),
		SYSTEM((byte)6),
		WHISPER((byte)7),
		ANNOUNCEMENT((byte)8),
		OBJECT((byte)9),
		OBJECT_WHISPER((byte)10);
		
		BedrockTextType(byte type){
			this.type = type;
		}
		
		private byte type;
		public byte getType() {
			return this.type;
		}
		
		public static BedrockTextType searchByType(byte type) {
			for(BedrockTextType types : BedrockTextType.values()) {
				if(types.getType() == type) {
					return types;
				}
			}
			return null;
		}
	}
	
	public BedrockTextType type;
	public boolean needsTranslation;
	public String sourceName;
	public String message;
	public List<String> params = new ArrayList<String>();
	public String xuid;
	public String platformChatId = "";
	
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeByte(this.type.getType());
		buf.writeBoolean(this.needsTranslation);
		switch(this.type) {
		case CHAT:
		case ANNOUNCEMENT:
		case WHISPER:
			ByteBufUtils.writeString(buf, this.sourceName);
			break;
		case RAW:
		case TIP:
		case SYSTEM:
			ByteBufUtils.writeString(buf, this.message);
		case TRANSLATION:
		case POPUP:
		case JUKEBOX:
			ByteBufUtils.writeString(buf, this.message);
			buf.writeInt(this.params.size());
			for(String param : this.params) {
				ByteBufUtils.writeString(buf, param);
			}
			break;
		}
		ByteBufUtils.writeString(buf, this.xuid);
		ByteBufUtils.writeString(buf, this.platformChatId);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.type = BedrockTextType.searchByType(buf.readByte());
		this.needsTranslation = buf.readBoolean();
		switch(this.type) {
		case CHAT:
		case ANNOUNCEMENT:
		case WHISPER:
			this.sourceName = ByteBufUtils.readString(buf);
			break;
		case RAW:
		case TIP:
		case SYSTEM:
			this.message = ByteBufUtils.readString(buf);
			break;
		case TRANSLATION:
		case POPUP:
		case JUKEBOX:
			this.message = ByteBufUtils.readString(buf);
			ArrayList<String> p = new ArrayList<String>();
			for(int i = 0; i < buf.readInt(); i++) {
				p.add(ByteBufUtils.readString(buf));
			}
			this.params = p;
			break;
		}
		this.xuid = ByteBufUtils.readString(buf);
		this.platformChatId = ByteBufUtils.readString(buf);
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
		return 0x09;
	}

}
