package net.novatech.jbprotocol.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CryptUtils {
	
	public static KeyGenerator AES_GEN;
	public static KeyGenerator EC_GEN; 
	public static PublicKey MOJANG_BEDROCK_PUBLIC_KEY;
	private static String MOJANG_BEDROCK_PUBLIC_KEY_STRING = "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAE8ELkixyLcwlZryUQcu1TvPOmI2B7vX83ndnWRUaXm74wFfa5f/lwQNTfrLVHa2PmenpGI6JhIMUJaWZrjmMj90NoKNFSNBuKdm8rYiXsfaz3K36x/1U26HpG0ZxK/V1V";
	
	static {
		try	{
			AES_GEN = KeyGenerator.getInstance("AES");
			EC_GEN = KeyGenerator.getInstance("EC");
			MOJANG_BEDROCK_PUBLIC_KEY = generatePublicKeyForBedrockFromBase(MOJANG_BEDROCK_PUBLIC_KEY_STRING);
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
	
	private static PublicKey generatePublicKeyForBedrockFromBase(String base) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(base)));
	}
	
}