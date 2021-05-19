package net.novatech.jbprotocol.bedrock.packets;

import net.novatech.jbprotocol.bedrock.packets.util.ResourcePackInfo;

import io.netty.buffer.ByteBuf;

import java.util.*;

public class ResourcePackInfoPacket extends BedrockPacket {

	public boolean mustAccept;
	public boolean scripting;
	public List<ResourcePackInfo> resources = new ArrayList<ResourcePackInfo>();
	public List<ResourcePackInfo> behaviours = new ArrayList<ResourcePackInfo>();
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeBoolean(this.mustAccept);
		buf.writeBoolean(this.scripting);
		buf.writeInt(this.resources.size());
		this.resources.forEach(resource -> {try {
			resource.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		}});
		buf.writeInt(this.behaviours.size());
		this.behaviours.forEach(behaviour -> {try {
			behaviour.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		};});
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.mustAccept = buf.readBoolean();
		this.scripting = buf.readBoolean();
		ArrayList<ResourcePackInfo> res = new ArrayList<ResourcePackInfo>();
		for(int i = 0; i < buf.readInt(); i++) {
			ResourcePackInfo info = new ResourcePackInfo();
			info.read(buf);
			res.add(info);
		}
		this.resources = res;
		
		ArrayList<ResourcePackInfo> beh = new ArrayList<ResourcePackInfo>();
		for(int i = 0; i < buf.readInt(); i++) {
			ResourcePackInfo info = new ResourcePackInfo();
			info.read(buf);
			beh.add(info);
		}
		this.behaviours = beh;
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
		return 0x06;
	}

}
