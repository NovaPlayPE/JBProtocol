package net.novatech.protocol;

import net.novatech.protocol.packet.AbstractPacket;

public abstract class GameListener {
	
	public abstract void receivePacket(AbstractPacket packet);
	
}