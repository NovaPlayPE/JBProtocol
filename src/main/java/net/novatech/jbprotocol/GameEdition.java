package net.novatech.jbprotocol;

import net.novatech.jbprotocol.bedrock.data.BedrockPong;
import net.novatech.jbprotocol.data.Pong;
import net.novatech.jbprotocol.java.data.JavaPong;
import net.novatech.library.reflection.Reflect;

public enum GameEdition {
	JAVA(JavaPong.class),
	BEDROCK(BedrockPong.class);
	
	private Class<? extends Pong> pongClass;

	GameEdition(Class<? extends Pong> pongClass) {
		this.pongClass = pongClass;
	}
	
	public Pong getInitialPong() {
		return Reflect.on(this.pongClass).newInstance();
	}
}