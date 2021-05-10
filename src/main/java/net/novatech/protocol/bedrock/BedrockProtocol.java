package net.novatech.protocol.bedrock;

import net.novatech.protocol.GameVersion;
import net.novatech.protocol.MinecraftProtocol;
import net.novatech.protocol.bedrock.packets.LoginPacket;

public class BedrockProtocol extends MinecraftProtocol {
	
	public BedrockProtocol() {
		this.gameVersion = GameVersion.BEDROCK;
		registerPackets();
	}
	
	public void registerPackets() {
		registerIncomingPacket((byte)0x01, LoginPacket.class);
	}
	
}