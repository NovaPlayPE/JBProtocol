package net.novatech.jbprotocol.listener;

import net.novatech.jbprotocol.packet.AbstractPacket;

public abstract class GameListener {
	
	public abstract void receivePacket(AbstractPacket packet);
	
}