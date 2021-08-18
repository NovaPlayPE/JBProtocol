package net.novatech.jbprotocol.bedrock.packets;

import io.netty.buffer.ByteBuf;
import net.novatech.library.io.ByteBufUtils;

public class EntityEventPacket extends BedrockPacket {
	
	public enum EntityEvent{
		HURT_ANIMATION(2),
		DEATH_ANIMATION(3),
		ARM_SWING(4),
		TAME_FAIL(6),
		TAME_SUCCESS(7),
		SHAKE_WET(8),
		USE_ITEM(9),
		EAT_GRASS_ANIMATION(10),
		FISH_HOOK_BUBBLE(11),
		FISH_HOOK_POSITION(12),
		FISH_HOOK_HOOK(13),
		FISH_HOOK_TEASE(14),
		SQUID_INK_CLOUD(15),
		ZOMBIE_VILLAGER_CURE(16),
		AMBIENT_SOUND(17),
		RESPAWN(18),
		IGOLEM_FLOWER_YES(19),
		IGOLEM_FLOWER_NO(20),
		LOVE_PARTICLES(21),
		WITCH_SPELL_PARTICLES(24),
		FIREWORK_EXPLOSION(25),
		SILVERFISH_SPAWN(27),
		ENCHANT(34),
		ELDER_GUARDIAN_CURSE(35),
		AGENT_ARM_SWING(36),
		ENDER_DRAGON_DEATH(37),
		DUST_PARTICLES(38),
		ARROW_SHAKE(39),
		EATING_ITEM(57),
		BABY_ANIMAL_FEED(60),
		DEATH_SMOKE_CLOUD(61),
		COMPLETE_TRADE(62),
		REMOVE_LEASH(63),
		CONSUME_TOTEM(65),
		PLAYER_CHECK_TREASURE_HUNTER_ACHIEVEMENT(66),
		ENTITY_SPAWN(67),
		DRAGON_PUKE(68),
		MERGE_ITEMS(69),
		
		NULL(-1);
		EntityEvent(int id){
			this.id = id;
		}
		
		public int id;
		public int getEventId() {
			return this.id;
		}
		
		public static EntityEvent searchById(int id){
			for(EntityEvent event : EntityEvent.values()) {
				if(event.getEventId() == id) {
					return event;
				}
			}
			return EntityEvent.NULL;
		}
		
	}
	
	public long runtimeId;
	public EntityEvent event;
	public int data;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		ByteBufUtils.writeUnsignedVarLong(buf, this.runtimeId);
		buf.writeByte(this.event.getEventId());
		ByteBufUtils.writeUnsignedVarInt(buf, this.data);
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		this.runtimeId = ByteBufUtils.readUnsignedVarLong(buf);
		this.event = EntityEvent.searchById(buf.readByte());
		this.data = ByteBufUtils.readUnsignedVarInt(buf);
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
		return 0x1B;
	}

}
