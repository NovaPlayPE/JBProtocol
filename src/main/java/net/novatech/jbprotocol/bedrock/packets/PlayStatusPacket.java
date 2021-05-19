package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

public class PlayStatusPacket extends BedrockPacket {

	public enum Status{
		LOGIN_SUCCESS(0),
		FAILED_CLIENT(1),
		FAILED_SERVER(2),
		PLAYER_SPAWN(3),
		FAILED_INVALID_TENANT(4),
		FAILED_VANILLA_EDU(5),
		FAILED_EDU_VANILLA(6),
		FAILED_SERVER_FULL(7);
		
		Status(int status){
			this.status = status;
		}
		@Getter
		private int status;
		
		public static Status searchByStatus(int status) {
			for(Status statuses : Status.values()) {
				if(statuses.getStatus() == status) {
					return statuses;
				}
			}
			return null;
		}
	}
	
	public Status status;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeInt(this.status.getStatus());
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.status = Status.searchByStatus(buf.readInt());
	}

	@Override
	public boolean isServerBound() {
		return false;
	}

	@Override
	public boolean isClientBound() {
		return true;
	}

	@Override
	public byte getId() {
		return 0x02;
	}

}
