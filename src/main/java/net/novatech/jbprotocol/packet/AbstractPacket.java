package net.novatech.jbprotocol.packet;

import io.netty.buffer.ByteBuf;

public abstract class AbstractPacket {
	
	public abstract void write(ByteBuf buf) throws Exception;
	public abstract void read(ByteBuf buf) throws Exception;
	
	public abstract boolean isServerBound();
	public abstract boolean isClientBound();
	
	public abstract byte getId();
	
}