package net.novatech.jbprotocol;

import java.lang.reflect.Constructor;
import java.util.*;
import lombok.Getter;
import net.novatech.jbprotocol.packet.AbstractPacket;

public abstract class MinecraftProtocol {
	
	@Getter
	public GameVersion gameVersion;
	
	public MinecraftProtocol() {
	}
	
	private HashMap<Byte, Class<? extends AbstractPacket>> general = new HashMap<Byte, Class<? extends AbstractPacket>>();
	private HashMap<Byte, Class<? extends AbstractPacket>> incoming = new HashMap<Byte, Class<? extends AbstractPacket>>();
	private HashMap<Byte, Class<? extends AbstractPacket>> outcoming = new HashMap<Byte, Class<? extends AbstractPacket>>();
	
	private HashMap<Class<? extends AbstractPacket>, Byte> outcomingPacket = new HashMap<Class<? extends AbstractPacket>, Byte>();
	
	public void registerPacket(byte id, Class<? extends AbstractPacket> packetClass) {
		this.general.put(id, packetClass);
		
		registerIncomingPacket(id, packetClass);
		registerOutcomingPacket(id, packetClass);
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
	
	public void registerIncomingPacket(byte id, Class<? extends AbstractPacket> packetClass) {
		this.incoming.put(id, packetClass);
		try {
			createIncoming(id);
		} catch(IllegalStateException ex) {
			this.incoming.remove(id);
		}
	}
	
	public void registerOutcomingPacket(byte id, Class<? extends AbstractPacket> packetClass) {
		this.outcoming.put(id, packetClass);
		this.outcomingPacket.put(packetClass, id);
	}
	
	private AbstractPacket createIncoming(byte id) {
		Class<? extends AbstractPacket> packetClass = incoming.get(id);
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