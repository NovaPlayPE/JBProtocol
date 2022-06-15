package net.novatech.jbprotocol.bedrock.packets;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;
import net.novatech.library.math.motion.Rotation;
import net.novatech.library.math.vector.Vector3f;
import net.novatech.library.math.vector.Vector3i;

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
	public boolean commandsEnabled;
	public boolean texturePackRequiered;
	public Map<String, Map<Integer, Object>> gameRules;
	public boolean bonusChest;
	public boolean hasStartWithMapEnabled;
	public int permissionLevel;
	public int chunkTickRate;
	public boolean behaviourLocked;
	public boolean resourcesLocked;
	public boolean isWorldLockedTemplate;
	public boolean onlyMsaGamertags;
	public boolean isWorldTemplate;
	public boolean isWorldOptionTemplate;
	public boolean enableV1Villagers;
	public String gameVersion;
	public int limitedWorldWidth;
	public int limitedWorldHeight;
	public boolean isNetherType;
	public boolean isForceExperimental;
	public String levelId;
	public String worldName;
	public String premiumWorldTemplateId;
	public boolean isTrial;
	public int movementType;
	public int movementRewindSize;
	public boolean authoritativeBlockBreaking;
	public long currentTick;
	public int enchantmentSeed;
	public String multiplayerCollerilationId;
	public boolean authoritativeServerInventories;
	public String serverEngine;
	
	
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
		buf.writeBoolean(this.commandsEnabled);
		buf.writeBoolean(this.texturePackRequiered);
		ByteBufUtils.writeUnsignedVarInt(buf, this.gameRules.size());
		for(Map.Entry<String, Map<Integer, Object>> gamerule : this.gameRules.entrySet()) {
			ByteBufUtils.writeString(buf, gamerule.getKey());
			for(Map.Entry<Integer, Object> gameruleData : gamerule.getValue().entrySet()) {
				buf.writeBoolean(false);
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
		buf.writeInt(this.chunkTickRate);
		buf.writeBoolean(this.behaviourLocked);
		buf.writeBoolean(this.resourcesLocked);
		buf.writeBoolean(this.isWorldLockedTemplate);
		buf.writeBoolean(this.onlyMsaGamertags);
		buf.writeBoolean(this.isWorldTemplate);
		buf.writeBoolean(this.isWorldOptionTemplate);
		buf.writeBoolean(this.enableV1Villagers);
		ByteBufUtils.writeString(buf, this.gameVersion);
		buf.writeInt(this.limitedWorldWidth);
		buf.writeInt(this.limitedWorldHeight);
		buf.writeBoolean(this.isNetherType);
		buf.writeBoolean(this.isForceExperimental);
		ByteBufUtils.writeString(buf, this.levelId);
		ByteBufUtils.writeString(buf, this.worldName);
		ByteBufUtils.writeString(buf, this.premiumWorldTemplateId);
		buf.writeBoolean(this.isTrial);
		ByteBufUtils.writeUnsignedVarInt(buf, this.movementType);
		buf.writeInt(this.movementRewindSize);
		buf.writeBoolean(this.authoritativeBlockBreaking);
		buf.writeLongLE(this.currentTick);
		ByteBufUtils.writeSignedVarInt(buf, this.enchantmentSeed);
		//todo: block properties and item states
		ByteBufUtils.writeString(buf, this.multiplayerCollerilationId);
		buf.writeBoolean(this.authoritativeServerInventories);
		ByteBufUtils.writeString(buf, this.serverEngine);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.entityUniqueId = ByteBufUtils.readSignedVarInt(buf);
		this.entityRuntimeId = ByteBufUtils.readUnsignedVarInt(buf);
		this.gamemode = ByteBufUtils.readSignedVarInt(buf);
		this.spawn = PacketHelper.readVector3f(buf);
		this.rotation = PacketHelper.readRotation2(buf);
		this.seed = ByteBufUtils.readSignedVarInt(buf);
		this.biomeType = buf.readShort();
		this.biomeName = ByteBufUtils.readString(buf);
		this.dimension = ByteBufUtils.readSignedVarInt(buf);
		this.generator = ByteBufUtils.readSignedVarInt(buf);
		this.worldGamemode = ByteBufUtils.readSignedVarInt(buf);
		this.difficulty = ByteBufUtils.readSignedVarInt(buf);
		this.worldSpawn = PacketHelper.readVector3i(buf);
		this.achievmentsDisabled = buf.readBoolean();
		this.dayCycle = ByteBufUtils.readSignedVarInt(buf);
		this.eduOffer = ByteBufUtils.readSignedVarInt(buf);
		this.eduEnabled = buf.readBoolean();
		this.eduId = ByteBufUtils.readString(buf);
		this.rainLevel = buf.readFloat();
		this.lightningLevel = buf.readFloat();
		this.hasConfirmedPlatformLockedContent = buf.readBoolean();
		this.isMultiplayer = buf.readBoolean();
		this.lanBroadcast = buf.readBoolean();
		this.xboxBroadcastMode = ByteBufUtils.readUnsignedVarInt(buf);
		this.platformBroadcastMode = ByteBufUtils.readUnsignedVarInt(buf);
		this.commandsEnabled = buf.readBoolean();
		this.texturePackRequiered = buf.readBoolean();
		
		Map<String, Map<Integer,Object>> grules = new HashMap<>();
		int length = ByteBufUtils.readUnsignedVarInt(buf);
		for(int i = 0; i < length; i++) {
			String gameRule = ByteBufUtils.readString(buf);
			Map<Integer, Object> gameruleData = new HashMap<Integer, Object>();
			buf.readBoolean();
			int type = ByteBufUtils.readUnsignedVarInt(buf);
			switch(type) {
			case 1:
				gameruleData.put(type, buf.readBoolean());
				break;
			case 2:
				gameruleData.put(type, ByteBufUtils.readUnsignedVarInt(buf));
				break;
			case 3:
				gameruleData.put(type, buf.readFloatLE());
				break;
			}
			grules.put(gameRule, gameruleData);
		}
		this.gameRules = grules;

		this.bonusChest = buf.readBoolean();
		this.hasStartWithMapEnabled = buf.readBoolean();
		this.permissionLevel = ByteBufUtils.readSignedVarInt(buf);
		this.chunkTickRate = buf.readInt();
		this.behaviourLocked = buf.readBoolean();
		this.resourcesLocked = buf.readBoolean();
		this.isWorldLockedTemplate = buf.readBoolean();
		this.onlyMsaGamertags = buf.readBoolean();
		this.isWorldTemplate = buf.readBoolean();
		this.isWorldOptionTemplate = buf.readBoolean();
		this.enableV1Villagers = buf.readBoolean();
		this.gameVersion = ByteBufUtils.readString(buf);
		this.limitedWorldWidth = buf.readInt();
		this.limitedWorldHeight = buf.readInt();
		this.isNetherType = buf.readBoolean();
		this.isForceExperimental = buf.readBoolean();
		this.levelId = ByteBufUtils.readString(buf);
		this.worldName = ByteBufUtils.readString(buf);
		this.premiumWorldTemplateId = ByteBufUtils.readString(buf);
		this.isTrial = buf.readBoolean();
		this.movementType = ByteBufUtils.readUnsignedVarInt(buf);
		this.movementRewindSize = buf.readInt();
		this.authoritativeBlockBreaking = buf.readBoolean();
		this.currentTick = buf.readLongLE();
		this.enchantmentSeed = ByteBufUtils.readSignedVarInt(buf);
		// todo: block properties and item states
		this.multiplayerCollerilationId = ByteBufUtils.readString(buf);
		this.authoritativeServerInventories = buf.readBoolean();
		this.serverEngine = ByteBufUtils.readString(buf);
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
