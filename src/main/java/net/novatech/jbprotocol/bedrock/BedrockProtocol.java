package net.novatech.jbprotocol.bedrock;

import net.novatech.jbprotocol.GameVersion;
import net.novatech.jbprotocol.MinecraftProtocol;
import net.novatech.jbprotocol.bedrock.packets.LoginPacket;

public class BedrockProtocol extends MinecraftProtocol {
	
	public BedrockProtocol() {
		this.gameVersion = GameVersion.BEDROCK;
		setProtocolVersion(440);
		registerPackets();
	}
	
	public void registerPackets() {
		this.registerServerboundPacket((byte)0x01, LoginPacket.class);
	}
	
}