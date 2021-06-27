package net.novatech.jbprotocol.bedrock;

import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.util.SessionData;

public class BedrockSessionData extends SessionData {
	
	@Getter
	@Setter
	public String xuid;
	
	@Getter
	@Setter
	public int guiScale;
	
	@Getter
	@Setter
	public String deviceID;
	
	@Getter
	@Setter
	public int deviceOS;
	
}