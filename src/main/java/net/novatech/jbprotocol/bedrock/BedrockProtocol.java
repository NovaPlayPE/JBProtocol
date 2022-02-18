package net.novatech.jbprotocol.bedrock;

import net.novatech.jbprotocol.GameEdition;
import net.novatech.jbprotocol.MinecraftProtocol;
import net.novatech.jbprotocol.bedrock.packets.*;

public class BedrockProtocol extends MinecraftProtocol {
	
	public BedrockProtocol(boolean client) {
		super(client);
		this.gameEdition = GameEdition.BEDROCK;
		this.gameVersion = "1.18.0.2";
		this.setProtocolVersion(475);
		registerPackets();
	}
	
	public void registerPackets() {
		this.registerServerboundPacket((byte)0x01, LoginPacket.class);
		this.registerClientboundPacket((byte)0x02, PlayStatusPacket.class);
		this.registerClientboundPacket((byte)0x03, ServerToClientHandshakePacket.class);
		this.registerServerboundPacket((byte)0x04, ClientToServerHandshakePacket.class);
		this.registerClientboundPacket((byte)0x05, DisconnectPacket.class);
		this.registerClientboundPacket((byte)0x06, ResourcePackInfoPacket.class);
		this.registerClientboundPacket((byte)0x07, ResourcePackStackPacket.class);
		this.registerPacket((byte)0x09, TextPacket.class);
		this.registerClientboundPacket((byte)0x0A, SetTimePacket.class);
		this.registerClientboundPacket((byte)0x0B, StartGamePacket.class);
		this.registerClientboundPacket((byte)0x0C, AddPlayerPacket.class);
		this.registerClientboundPacket((byte)0x0D, AddActorPacket.class);
		this.registerClientboundPacket((byte)0x0E, RemoveActorPacket.class);
		this.registerClientboundPacket((byte)0x0F, AddItemEntityPacket.class);
		this.registerClientboundPacket((byte)0x11, TakeItemEntityPacket.class);
		this.registerPacket((byte)0x12, MoveActorAbsolutePacket.class);
		this.registerPacket((byte)0x13, MovePlayerPacket.class);
		this.registerPacket((byte)0x14, RiderJumpPacket.class);
		this.registerClientboundPacket((byte)0x15, UpdateBlockPacket.class);
		this.registerClientboundPacket((byte)0x16, AddPaintingPacket.class);
		this.registerPacket((byte)0x17, TickSyncPacket.class);
		this.registerPacket((byte)0x18, LevelSoundEvent_V1_Packet.class);
		this.registerPacket((byte)0x19, LevelEventPacket.class);
		this.registerClientboundPacket((byte)0x1A, BlockEventPacket.class);
		this.registerPacket((byte)0x1B, EntityEventPacket.class);
		
		this.registerClientboundPacket((byte)0x3A, LevelChunkPacket.class);
		this.registerClientboundPacket((byte)0x4A, BossEventPacket.class);
		
		this.registerPacket((byte)0xFF, Wrapper.class);
	}
	
}