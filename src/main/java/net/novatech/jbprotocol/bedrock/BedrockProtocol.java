package net.novatech.jbprotocol.bedrock;

import net.novatech.jbprotocol.GameEdition;
import net.novatech.jbprotocol.MinecraftProtocol;
import net.novatech.jbprotocol.bedrock.packets.*;

public class BedrockProtocol extends MinecraftProtocol {
	
	public BedrockProtocol(boolean client) {
		super(client);
		this.gameVersion = GameEdition.BEDROCK;
		setProtocolVersion(440);
		registerPackets();
	}
	
	public void registerPackets() {
		this.registerServerboundPacket((byte)0x01, LoginPacket.class);
		this.registerClientboundPacket((byte)0x02, PlayStatusPacket.class);
		this.registerClientboundPacket((byte)0x03, ServerToClientHandshakePacket.class);
		this.registerServerboundPacket((byte)0x04, ClientToServerHandshakePacket.class);
		this.registerClientboundPacket((byte)0x05, DissconnectPacket.class);
		this.registerClientboundPacket((byte)0x06, ResourcePackInfoPacket.class);
		
		this.registerPacket((byte)0x09, TextPacket.class);
	}
	
}