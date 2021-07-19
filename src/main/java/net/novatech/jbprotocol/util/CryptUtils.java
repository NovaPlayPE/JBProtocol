package net.novatech.jbprotocol.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptUtils {
	
	public static KeyGenerator AES_GEN;
	public static KeyGenerator EC_GEN; 
	public static KeyPair JAVA_KEY;
	public static PublicKey MOJANG_BEDROCK_PUBLIC_KEY;
	private static String MOJANG_BEDROCK_PUBLIC_KEY_STRING = "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAE8ELkixyLcwlZryUQcu1TvPOmI2B7vX83ndnWRUaXm74wFfa5f/lwQNTfrLVHa2PmenpGI6JhIMUJaWZrjmMj90NoKNFSNBuKdm8rYiXsfaz3K36x/1U26HpG0ZxK/V1V";
	
	static {
		try	{
			AES_GEN = KeyGenerator.getInstance("AES");
			EC_GEN = KeyGenerator.getInstance("EC");
			
			AES_GEN.init(128);
			EC_GEN.init(128);
			
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(1024);
			JAVA_KEY = generator.generateKeyPair();
			
			MOJANG_BEDROCK_PUBLIC_KEY = generatePublicKeyForBedrockFromBase(MOJANG_BEDROCK_PUBLIC_KEY_STRING);
		} catch(Exception ex) {
			
		}
	}
	
	public static String generateServerId(SecretKey sKey, PublicKey pKey) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update("".getBytes(StandardCharsets.ISO_8859_1));
			digest.update(sKey.getEncoded());
			digest.update(pKey.getEncoded());
			return new BigInteger(digest.digest()).toString(16);
		} catch(Exception ex) {
			return null;
		}
	}
	
	public static PublicKey generatePublicKeyForJavaFromBase(byte[] base) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(base));
	}
	
	public static SecretKey generateSecretKeyForJava() {
		SecretKey key = null;
		try {
			key = AES_GEN.generateKey();
		} catch(Exception ex) {}
		return key;
	}
	
	public static SecretKey secretFromShared(PrivateKey pKey, byte[] sharedKey) {
		return new SecretKeySpec(encrypt(Cipher.DECRYPT_MODE, pKey, sharedKey), "AES");
	}
	
	private static byte[] encrypt(int cipherMode, Key key, byte[] encoded) {
		try {
			Cipher cipher = Cipher.getInstance(key.getAlgorithm().equals("RSA") ? "RSA/ECB/PKCS1Padding" : "AES/CFB8/NoPadding");
			cipher.init(cipherMode, key);
			return cipher.doFinal(encoded);
		} catch(Exception ex) {
			return null;
		}
	}
	
	private static PublicKey generatePublicKeyForBedrockFromBase(String base) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(base)));
	}
	
}