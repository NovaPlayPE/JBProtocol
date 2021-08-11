package net.novatech.jbprotocol.bedrock.packets;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.bedrock.packets.util.ResourcePackInfo;
import net.novatech.library.io.ByteBufUtils;

public class ResourcePackStackPacket extends BedrockPacket {

	public boolean mustAccept;
	public List<ResourcePackInfo> resources = new ArrayList<ResourcePackInfo>();
	public List<ResourcePackInfo> behaviours = new ArrayList<ResourcePackInfo>();
	public boolean experimental;
	public String gameVersion;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeBoolean(this.mustAccept);
		buf.writeInt(this.resources.size());
		this.resources.forEach(resource -> {try {
			ByteBufUtils.writeString(buf, resource.packId);
			ByteBufUtils.writeString(buf, resource.packVersion);
			ByteBufUtils.writeString(buf, resource.subPackName);
		} catch (Exception e) {
			e.printStackTrace();
		}});
		buf.writeInt(this.behaviours.size());
		this.behaviours.forEach(behaviour -> {try {
			ByteBufUtils.writeString(buf, behaviour.packId);
			ByteBufUtils.writeString(buf, behaviour.packVersion);
			ByteBufUtils.writeString(buf, behaviour.subPackName);
		} catch (Exception e) {
			e.printStackTrace();
		};});
		buf.writeBoolean(this.experimental);
		ByteBufUtils.writeString(buf, this.gameVersion);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.mustAccept = buf.readBoolean();
		ArrayList<ResourcePackInfo> res = new ArrayList<ResourcePackInfo>();
		for(int i = 0; i < buf.readInt(); i++) {
			ResourcePackInfo info = new ResourcePackInfo();
			info.packId = ByteBufUtils.readString(buf);
			info.packVersion = ByteBufUtils.readString(buf);
			info.subPackName = ByteBufUtils.readString(buf);
			res.add(info);
		}
		this.resources = res;
		
		ArrayList<ResourcePackInfo> beh = new ArrayList<ResourcePackInfo>();
		for(int i = 0; i < buf.readInt(); i++) {
			ResourcePackInfo info = new ResourcePackInfo();
			info.packId = ByteBufUtils.readString(buf);
			info.packVersion = ByteBufUtils.readString(buf);
			info.subPackName = ByteBufUtils.readString(buf);
			beh.add(info);
		}
		this.behaviours = beh;
		this.experimental = buf.readBoolean();
		this.gameVersion = ByteBufUtils.readString(buf);
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
		return 0x07;
	}

}
