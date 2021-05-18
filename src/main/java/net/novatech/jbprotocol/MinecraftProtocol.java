package net.novatech.jbprotocol;

import java.lang.reflect.Constructor;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.packet.AbstractPacket;

public abstract class MinecraftProtocol {
	
	@Getter
	public GameVersion gameVersion;
	@Getter
	@Setter
	public int protocolVersion;
	
	public MinecraftProtocol() {
	}
	
	private HashMap<Byte, Class<? extends AbstractPacket>> general = new HashMap<Byte, Class<? extends AbstractPacket>>();
	private HashMap<Byte, Class<? extends AbstractPacket>> serverbound = new HashMap<Byte, Class<? extends AbstractPacket>>();
	private HashMap<Byte, Class<? extends AbstractPacket>> clientbound = new HashMap<Byte, Class<? extends AbstractPacket>>();
	
	private HashMap<Class<? extends AbstractPacket>, Byte> clientboundPacket = new HashMap<Class<? extends AbstractPacket>, Byte>();
	
	public void registerPacket(byte id, Class<? extends AbstractPacket> packetClass) {
		this.general.put(id, packetClass);
		
		registerServerboundPacket(id, packetClass);
		registerClientboundPacket(id, packetClass);
	}
	
	public Class<? extends AbstractPacket> searchPacketClass(byte id){
		return this.general.get(id);
	}
	
	public AbstractPacket createPacket(byte id) {
		Class<? extends AbstractPacket> clazz = searchPacketClass(id);
		try {
			Constructor<? extends AbstractPacket> constructor = clazz.getConstructor(AbstractPacket.class);
			AbstractPacket pk = constructor.newInstance();
			return pk;
		} catch(Exception ex) {}
		return null;
	}
	
	public void registerServerboundPacket(byte id, Class<? extends AbstractPacket> packetClass) {
		this.serverbound.put(id, packetClass);
		try {
			createIncoming(id);
		} catch(IllegalStateException ex) {
			this.serverbound.remove(id);
		}
	}
	
	public void registerClientboundPacket(byte id, Class<? extends AbstractPacket> packetClass) {
		this.clientbound.put(id, packetClass);
		this.clientboundPacket.put(packetClass, id);
	}
	
	private AbstractPacket createIncoming(byte id) {
		Class<? extends AbstractPacket> packetClass = clientbound.get(id);
		if(packetClass == null) {
			throw new NullPointerException("Packet is not registered");
		}
		
		try {
			Constructor<? extends AbstractPacket> constructor = packetClass.getConstructor(AbstractPacket.class);
			AbstractPacket pk = constructor.newInstance();
			return pk;
		} catch(Exception ex) {}
		return null;
	}
	
}