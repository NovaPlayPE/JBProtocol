package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.library.math.Rotation;
import net.novatech.library.math.Vector3f;
import net.novatech.library.math.Vector3i;

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
	public int dayCicle;
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
	
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isServerBound() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClientBound() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
