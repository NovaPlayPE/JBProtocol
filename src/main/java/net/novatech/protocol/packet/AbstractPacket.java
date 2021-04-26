package net.novatech.protocol.packet;

import io.netty.buffer.ByteBuf;

public abstract class AbstractPacket {
	
	public abstract void write(ByteBuf buf) throws Exception;
	public abstract void read(ByteBuf buf) throws Exception;
	
}