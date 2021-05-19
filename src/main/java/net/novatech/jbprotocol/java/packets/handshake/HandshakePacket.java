package net.novatech.jbprotocol.java.packets.handshake;

import io.netty.buffer.ByteBuf;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.library.utils.ByteBufUtils;

public class HandshakePacket extends JavaPacket {

	public enum HandshakeState{
		STATUS(1),
		LOGIN(2);
		
		HandshakeState(int state){
			this.state = state;
		}
		private int state;
		public int getState() {
			return this.state;
		}
		
		public static HandshakeState searchByState(int state) {
			for(HandshakeState states : HandshakeState.values()) {
				if(states.getState() == state) {
					return states;
				}
			}
			return null;
		}
	}
	
	public int protocolVersion;
	public String hostName;
	public int port = 25565;
	public HandshakeState state;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarInt(buf, this.protocolVersion);
		ByteBufUtils.writeString(buf, this.hostName);
		buf.writeShort(this.port);
		ByteBufUtils.writeUnsignedVarInt(buf, this.state.getState());
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.protocolVersion = ByteBufUtils.readUnsignedVarInt(buf);
		this.hostName = ByteBufUtils.readString(buf);
		this.port = buf.readUnsignedShort();
		this.state = HandshakeState.searchByState(ByteBufUtils.readUnsignedVarInt(buf));
	}

	@Override
	public boolean isServerBound() {
		return true;
	}

	@Override
	public boolean isClientBound() {
		return false;
	}

	@Override
	public byte getId() {
		return 0x00;
	}

}
