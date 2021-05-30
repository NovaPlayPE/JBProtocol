package net.novatech.jbprotocol.bedrock.packets;

import java.util.Map;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.math.Rotation;
import net.novatech.library.math.Vector3f;
import net.novatech.library.math.Vector3i;
import net.novatech.library.utils.ByteBufUtils;

public class StartGamePacket extends BedrockPacket {
	
	public long entityUniqueId;
	public long entityRuntimeId;
	public int gamemode;
	public Vector3f spawn;
	public Rotation rotation;
	public int seed;
	public short biomeType;
	public String biomeName;
	public int dimension;
	public int generator;
	public int worldGamemode;
	public int difficulty;
	public Vector3i worldSpawn;
	public boolean achievmentsDisabled;
	public int dayCycle;
	public int eduOffer;
	public boolean eduEnabled;
	public String eduId;
	public float rainLevel;
	public float lightningLevel;
	public boolean hasConfirmedPlatformLockedContent;
	public boolean isMultiplayer;
	public boolean lanBroadcast;
	public int xboxBroadcastMode;
	public int platformBroadcastMode;
	public Map<String, Map<Integer, Object>> gameRules;
	public boolean bonusChest;
	public boolean hasStartWithMapEnabled;
	public int permissionLevel;
	
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeSignedVarLong(buf, this.entityUniqueId);
		ByteBufUtils.writeUnsignedVarLong(buf, this.entityRuntimeId);
		ByteBufUtils.writeSignedVarInt(buf, this.gamemode);
		PacketHelper.writeVector3f(buf, this.spawn);
		PacketHelper.writeRotation2(buf, this.rotation);
		ByteBufUtils.writeSignedVarInt(buf, this.seed);
		buf.writeShort(this.biomeType);
		ByteBufUtils.writeString(buf, this.biomeName);
		ByteBufUtils.writeSignedVarInt(buf, this.dimension);
		ByteBufUtils.writeSignedVarInt(buf, this.generator);
		ByteBufUtils.writeSignedVarInt(buf, this.worldGamemode);
		ByteBufUtils.writeSignedVarInt(buf, this.difficulty);
		PacketHelper.writeVector3i(buf, this.worldSpawn);
		buf.writeBoolean(this.achievmentsDisabled);
		ByteBufUtils.writeSignedVarInt(buf, this.dayCycle);
		ByteBufUtils.writeSignedVarInt(buf, this.eduOffer);
		buf.writeBoolean(this.eduEnabled);
		ByteBufUtils.writeString(buf, this.eduId);
		buf.writeFloat(this.rainLevel);
		buf.writeFloat(this.lightningLevel);
		buf.writeBoolean(this.hasConfirmedPlatformLockedContent);
		buf.writeBoolean(this.isMultiplayer);
		buf.writeBoolean(this.lanBroadcast);
		ByteBufUtils.writeUnsignedVarInt(buf, this.xboxBroadcastMode);
		ByteBufUtils.writeUnsignedVarInt(buf, this.platformBroadcastMode);
		ByteBufUtils.writeUnsignedVarInt(buf, this.gameRules.size());
		for(Map.Entry<String, Map<Integer, Object>> gamerule : this.gameRules.entrySet()) {
			ByteBufUtils.writeString(buf, gamerule.getKey());
			for(Map.Entry<Integer, Object> gameruleData : gamerule.getValue().entrySet()) {
				ByteBufUtils.writeUnsignedVarInt(buf, gameruleData.getKey());
				switch(gameruleData.getKey()) {
				case 1:
					buf.writeBoolean((Boolean)gameruleData.getValue());
					break;
				case 2:
					ByteBufUtils.writeUnsignedVarInt(buf, (Integer)gameruleData.getValue());
					break;
				case 3:
					buf.writeFloatLE((float)gameruleData.getValue());
					break;
				}
			}
		}
		buf.writeBoolean(this.bonusChest);
		buf.writeBoolean(this.hasStartWithMapEnabled);
		ByteBufUtils.writeSignedVarInt(buf, this.permissionLevel);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		// TODO Auto-generated method stub

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
		return 0x0B;
	}

}
