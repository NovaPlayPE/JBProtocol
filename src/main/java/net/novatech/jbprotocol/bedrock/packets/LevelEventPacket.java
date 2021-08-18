package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;

import net.novatech.library.math.Vector3f;
import net.novatech.jbprotocol.packet.PacketHelper;
import net.novatech.library.io.ByteBufUtils;

public class LevelEventPacket extends BedrockPacket {
	
	public enum LevelEvent{
		
		SOUND_CLICK(1000),
		SOUND_CLICK_FAIL(1001),
		SOUND_SHOOT(1002),
		SOUND_DOOR(1003),
		SOUND_FIZZ(1004),
		SOUND_TNT(1005),
		SOUND_GHAST(1007),
		SOUND_BLAZE_SHOOT(1008),
		SOUND_GHAST_SHOOT(1009),
		SOUND_DOOR_BUMP(1010),
		SOUND_DOOR_CRASH(1012),
		SOUND_ENDERMAN_TELEPORT(1018),
		SOUND_ANVIL_BREAK(1020),
		SOUND_ANVIL_USE(1021),
		SOUND_ANVIL_FALL(1022),
		SOUND_ITEM_DROP(1030),
		SOUND_ITEM_THROWN(1031),
		SOUND_ITEMFRAME_ITEM_ADDED(1040),
		SOUND_ITEMFRAME_PLACED(1041),
		SOUND_ITEMFRAME_REMOVED(1042),
		SOUND_ITEMFRAME_ITEM_REMOVED(1043),
		SOUND_ITEMRAME_ITEM_ROTATED(1044),
		SOUND_ARMORSTAND_BREAK(1060),
		SOUND_ARMORSTAND_HIT(1061),
		SOUND_ARMORSTAND_FALL(1062),
		SOUND_ARMORSTAND_PLACE(1063),
		
		GURDIAN_CURSE(2006),
		
		NULL(0);
		
		LevelEvent(int id){
			this.id = id;
		}
		
		public int id;
		public int getEventId() {
			return this.id;
		}
		
		public static LevelEvent searchById(int id){
			for(LevelEvent event : LevelEvent.values()) {
				if(event.getEventId() == id) {
					return event;
				}
			}
			return LevelEvent.NULL;
		}
		
	}
	
	public LevelEvent levelEvent;
	public Vector3f position;
	public int data;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeSignedVarInt(buf, this.levelEvent.getEventId());
		PacketHelper.writeVector3f(buf, this.position);
		ByteBufUtils.writeSignedVarInt(buf, this.data);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.levelEvent = LevelEvent.searchById(ByteBufUtils.readSignedVarInt(buf));
		this.position = PacketHelper.readVector3f(buf);
		this.data = ByteBufUtils.readSignedVarInt(buf);
	}

	@Override
	public boolean isServerBound() {
		return true;
	}

	@Override
	public boolean isClientBound() {
		return true;
	}

	@Override
	public byte getId() {
		return 0x19;
	}

}
