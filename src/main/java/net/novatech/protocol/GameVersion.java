package net.novatech.protocol;

public enum GameVersion {
	JAVA("1.17.0"),
	BEDROCK("1.17.0");
	
	GameVersion(String version) {
		this.version = version;
	}
	
	private String version;
	public String getVersion() {
		return version;
	}
}