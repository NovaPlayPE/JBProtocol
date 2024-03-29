package net.novatech.jbprotocol.java;

import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.java.packets.*;
import net.novatech.jbprotocol.java.packets.handshake.HandshakePacket;
import net.novatech.jbprotocol.java.packets.login.*;
import net.novatech.jbprotocol.java.packets.status.*;
import net.novatech.jbprotocol.java.packets.play.serverbound.*;
import net.novatech.jbprotocol.java.packets.play.clientbound.*;
import net.novatech.jbprotocol.GameEdition;
import net.novatech.jbprotocol.MinecraftProtocol;

public class JavaProtocol extends MinecraftProtocol {
	
	@Getter
	@Setter
	public JavaGameState gameState = JavaGameState.HANDSHAKE;
	
	public JavaProtocol(boolean client) {
		super(client);
		this.gameEdition = GameEdition.JAVA;
		this.gameVersion = "1.19.3";
		this.setProtocolVersion(761);
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
		case GAME:
			//clientbound
			this.registerClientboundPacket((byte)0x00, SpawnEntityPacket.class);
			this.registerClientboundPacket((byte)0x01, SpawnOrbExperiencePacket.class);
			this.registerClientboundPacket((byte)0x02, SpawnEntityLivingPacket.class);
			this.registerClientboundPacket((byte)0x03, SpawnPaintingPacket.class);
			this.registerClientboundPacket((byte)0x04, SpawnPlayerPacket.class);
			this.registerClientboundPacket((byte)0x06, SculkVibrationPacket.class);
			this.registerClientboundPacket((byte)0x06, ClientEntityAnimationPacket.class);
			this.registerClientboundPacket((byte)0x08, AcknowledgePlayerDiggingPacket.class);
			this.registerClientboundPacket((byte)0x09, BlockBreakAnimationPacket.class);
			this.registerClientboundPacket((byte)0x0A, BlockEntityDataPacket.class);
			this.registerClientboundPacket((byte)0x0B, BlockActionPacket.class);
			this.registerClientboundPacket((byte)0x0C, BlockChangePacket.class);
			this.registerClientboundPacket((byte)0x0D, BossBarPacket.class);
			this.registerClientboundPacket((byte)0x0E, ServerDifficultyPacket.class);
			this.registerClientboundPacket((byte)0x0F, ClientChatPacket.class);
			this.registerClientboundPacket((byte)0x10, ClearTitlesPacket.class);
			this.registerClientboundPacket((byte)0x11, TabCompletePacket.class);
			
			//serverbound
			this.registerServerboundPacket((byte)0x03, ServerChatPacket.class);
		break;
		}
	}
	
}