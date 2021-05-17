package net.novatech.jbprotocol.java;

import lombok.Getter;
import net.novatech.jbprotocol.java.packets.*;
import net.novatech.jbprotocol.GameVersion;
import net.novatech.jbprotocol.MinecraftProtocol;

public class JavaProtocol extends MinecraftProtocol {
	
	@Getter
	public JavaGameState gameState = JavaGameState.HANDSHAKE;
	
	public JavaProtocol() {
		this.gameVersion = GameVersion.JAVA;
	}
	
	public void registerPackets() {
		switch(this.getGameState()) {
		case LOGIN:
			this.registerServerboundPacket((byte)0x00, LoginStartPacket.class);
			this.registerServerboundPacket((byte)0x01, EncryptionResponsePacket.class);
			this.registerServerboundPacket((byte)0x02, LoginPluginResponsePacket.class);
		break;
		}
	}
	
}