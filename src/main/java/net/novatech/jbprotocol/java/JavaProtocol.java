package net.novatech.jbprotocol.java;

import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.java.packets.*;
import net.novatech.jbprotocol.java.packets.handshake.HandshakePacket;
import net.novatech.jbprotocol.java.packets.login.*;
import net.novatech.jbprotocol.java.packets.status.*;
import net.novatech.jbprotocol.GameEdition;
import net.novatech.jbprotocol.MinecraftProtocol;

public class JavaProtocol extends MinecraftProtocol {
	
	@Getter
	@Setter
	public JavaGameState gameState = JavaGameState.HANDSHAKE;
	
	public JavaProtocol(boolean client) {
		super(client);
		this.gameVersion = GameEdition.JAVA;
		setProtocolVersion(0x4000001B);
	}
	
	public void registerPackets() {
		this.clearAll();
		
		switch(this.getGameState()) {
		case HANDSHAKE:
			this.registerServerboundPacket((byte)0x00, HandshakePacket.class);
			break;
		case STATUS:
			this.registerServerboundPacket((byte)0x00, RequestPacket.class);
			this.registerServerboundPacket((byte)0x01, ServerPingPacket.class);
			
			this.registerClientboundPacket((byte)0x00, ResponsePacket.class);
			this.registerClientboundPacket((byte)0x01, ServerPongPacket.class);
			break;
		case LOGIN:
			this.registerServerboundPacket((byte)0x00, LoginStartPacket.class);
			this.registerServerboundPacket((byte)0x01, EncryptionResponsePacket.class);
			this.registerServerboundPacket((byte)0x02, LoginPluginResponsePacket.class);
			
			this.registerClientboundPacket((byte)0x00, DissconnectPacket.class);
			this.registerClientboundPacket((byte)0x01, EncryptionRequestPacket.class);
			this.registerClientboundPacket((byte)0x02, LoginSuccessPacket.class);
			this.registerClientboundPacket((byte)0x03, SetCompressionPacket.class);
			this.registerClientboundPacket((byte)0x04, LoginPluginRequestPacket.class);
		break;
		}
	}
	
}