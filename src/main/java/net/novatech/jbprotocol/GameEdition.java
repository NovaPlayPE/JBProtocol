package net.novatech.jbprotocol;

public enum GameEdition {
	JAVA("1.17.0"),
	BEDROCK("1.17.0");
	
	GameEdition(String version) {
		this.version = version;
	}
	
	private String version;
	public String getVersion() {
		return version;
	}
}