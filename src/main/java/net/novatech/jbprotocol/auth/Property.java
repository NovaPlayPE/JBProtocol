package net.novatech.jbprotocol.auth;

import lombok.Getter;

public class Property{
	
	@Getter
	private String property;
	@Getter
	private String value;
	
	public Property(String property, String value) {
		this.property = property;
		this.value = value;
	}
	
}