package net.novatech.jbprotocol.java;

import lombok.Getter;
import net.novatech.jbprotocol.GameVersion;
import net.novatech.jbprotocol.MinecraftProtocol;

public class JavaProtocol extends MinecraftProtocol {
	
	@Getter
	public JavaGameState gameState = JavaGameState.LOGIN;
	
	public JavaProtocol() {
		this.gameVersion = GameVersion.JAVA;
	}
	
}