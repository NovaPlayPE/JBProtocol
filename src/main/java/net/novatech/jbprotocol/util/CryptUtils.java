package net.novatech.jbprotocol.util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CryptUtils {
	
	public static KeyGenerator AES_GEN;
	public static KeyGenerator EC_GEN; 
	static {
		try	{
			AES_GEN = KeyGenerator.getInstance("AES");
			EC_GEN = KeyGenerator.getInstance("EC");
		} catch(Exception ex) {
			
		}
	}
	public SecretKey generateSecretKeyForJava() {
		SecretKey key = null;
		try {
			key = AES_GEN.generateKey();
		} catch(Exception ex) {}
		return key;
	}
	
}