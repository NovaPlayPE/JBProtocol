package net.novatech.jbprotocol.bedrock.packets.util;

import io.netty.buffer.ByteBuf;
import net.novatech.library.utils.ByteBufUtils;

public class ResourcePackInfo {
	
	public String packId;
	public String packVersion;
	public long packSize;
	public String contentKey;
	public String subPackName;
	public String contentId;
	public boolean scripting;
	public boolean rayTracingCapable;
	
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeString(buf, this.packId);
		ByteBufUtils.writeString(buf, this.packVersion);
		buf.writeLong(this.packSize);
		ByteBufUtils.writeString(buf, this.contentKey);
		ByteBufUtils.writeString(buf, this.subPackName);
		ByteBufUtils.writeString(buf, this.contentId);
		buf.writeBoolean(this.scripting);
		buf.writeBoolean(this.rayTracingCapable);
	}
	
	public void read(ByteBuf buf) throws Exception {
		this.packId = ByteBufUtils.readString(buf);
		this.packVersion = ByteBufUtils.readString(buf);
		this.packSize = buf.readLong();
		this.contentKey = ByteBufUtils.readString(buf);
		this.subPackName = ByteBufUtils.readString(buf);
		this.contentId = ByteBufUtils.readString(buf);
		this.scripting = buf.readBoolean();
		this.rayTracingCapable = buf.readBoolean();
	}
	
}