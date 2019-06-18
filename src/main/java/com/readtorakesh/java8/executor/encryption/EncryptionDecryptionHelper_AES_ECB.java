package com.readtorakesh.java8.executor.encryption;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionDecryptionHelper_AES_ECB {

	public static void main(String[] args) throws Exception {
		EncryptionDecryptionHelper_AES_ECB mainApp = new EncryptionDecryptionHelper_AES_ECB();
		
		System.out.println("-----------------------------------------------------------------");
		System.out.println("Algoritham \t: AES with ECB (Electronic Codebook) block mode");
		byte[] secretKeyBytes = mainApp.generateSecretKeyBytes();
		System.out.println("Key Length \t: " + (secretKeyBytes.length * 8) + " bits");
		System.out.println("-----------------------------------------------------------------");
		
		String plainText = "abc";
		System.out.println("Plain Text \t: " + plainText);
		
		String encryptedBase64 = mainApp.encrypt(plainText, secretKeyBytes);
		System.out.println("Encrypted Text \t: " + encryptedBase64);

		String decryptedText = mainApp.decrypt(encryptedBase64, secretKeyBytes);
		System.out.println("Decrypted Text \t: " + decryptedText);
		
		System.out.println("\n");
		System.out.println("Encrypted same text multiple times");
		System.out.println("Plain Text : " + plainText + " | Encrypted Text : " + mainApp.encrypt(plainText, secretKeyBytes));
		System.out.println("Plain Text : " + plainText + " | Encrypted Text : " + mainApp.encrypt(plainText, secretKeyBytes));
		System.out.println("Plain Text : " + plainText + " | Encrypted Text : " + mainApp.encrypt(plainText, secretKeyBytes));
	}
	
	private String encrypt(String plainText, byte[] secretKeyBytes) throws Exception{
		SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		
		String encryptedMesssageBase64 =  Base64.getEncoder().encodeToString(encryptedBytes);
		return encryptedMesssageBase64;
	}
	
	private String decrypt(String encryptedBase64, byte[] secretKeyBytes) throws Exception {
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedBase64);
		
		SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		
		String decryptedText = new String(decryptedBytes, "UTF-8");
		
		return decryptedText;
	}
	
	private byte[] generateSecretKeyBytes() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}
	
}
