package net.novatech.protocol.bedrock;

import net.novatech.protocol.GameProtocol;
import net.novatech.protocol.MinecraftProtocol;
import net.novatech.protocol.bedrock.packets.LoginPacket;

public class BedrockProtocol extends MinecraftProtocol {
	
	public BedrockProtocol() {
		this.gameVersion = GameProtocol.BEDROCK;
		registerPackets();
	}
	
	public void registerPackets() {
		registerIncomingPacket((byte)0x01, LoginPacket.class);
	}
	
}