package net.novatech.jbprotocol.java.packets.play.clientbound;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.utils.ByteBufUtils;

public class TabCompletePacket extends JavaPacket {
	
	public int id;
	public int start;
	public int length;
	public int count;
	public String[] matches;
	public String[] tooltips;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.id);
		ByteBufUtils.writeUnsignedVarInt(buf, this.start);
		ByteBufUtils.writeUnsignedVarInt(buf, this.length);
		ByteBufUtils.writeUnsignedVarInt(buf, this.count);
		for(int i = 0; i < this.matches.length; i++) {
			ByteBufUtils.writeString(buf, this.matches[i]);
			String tooltip = this.tooltips[i];
			if(tooltip != null) {
				buf.writeBoolean(true);
				ByteBufUtils.writeString(buf, tooltip);
			} else {
				buf.writeBoolean(false);
			}
		}
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.id = ByteBufUtils.readUnsignedVarInt(buf);
		this.start = ByteBufUtils.readUnsignedVarInt(buf);
		this.length = ByteBufUtils.readUnsignedVarInt(buf);
		this.count = ByteBufUtils.readUnsignedVarInt(buf);
		this.matches = new String[this.count];
		this.tooltips = new String[this.count];
		for(int i = 0; i < this.count; i++) {
			this.matches[i] = ByteBufUtils.readString(buf);
			if(buf.readBoolean()) {
				this.tooltips[i] = ByteBufUtils.readString(buf);
			}
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
		return 0x11;
	}

}
