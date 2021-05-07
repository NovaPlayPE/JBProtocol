package net.novatech.protocol;

public enum GameProtocol {
	JAVA("1.17.0"),
	BEDROCK("1.17.0");
	
	GameProtocol(String version) {
		this.version = version;
	}
	
	private String version;
	public String getVersion() {
		return version;
	}
}