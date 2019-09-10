package com.readtorakesh.java8.encryption;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionDecryptionHelper_AES_CBC {

	public static void main(String[] args) throws Exception {
		EncryptionDecryptionHelper_AES_CBC mainApp = new EncryptionDecryptionHelper_AES_CBC();
		
		System.out.println("-----------------------------------------------------------------");
		System.out.println("Algoritham \t: AES with CBC (Cipher Block Chaining) block mode");
		byte[] secretKeyBytes = mainApp.generateSecretKeyBytes();
		System.out.println("Key Length \t: " + (secretKeyBytes.length * 8) + " bits");
		System.out.println("-----------------------------------------------------------------");
		
		String plainText = "read2rakesh";
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
		
		byte[] ivBytes = generateIvBytes();
		IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		
		ByteBuffer encryptedMessage = ByteBuffer.allocate(1 + ivBytes.length  + encryptedBytes.length);
		encryptedMessage.put((byte)ivBytes.length);
		encryptedMessage.put(ivBytes);
		encryptedMessage.put(encryptedBytes);
		
		byte[] encryptedMesssageBytes = encryptedMessage.array();
		String encryptedMesssageBase64 =  Base64.getEncoder().encodeToString(encryptedMesssageBytes);
		
		return encryptedMesssageBase64;
	}
	
	private String decrypt(String encryptedBase64, byte[] secretKeyBytes) throws Exception {
		byte[] encryptedMesssageBytes = Base64.getDecoder().decode(encryptedBase64);
		ByteBuffer encryptedMessageByteBuffer = ByteBuffer.wrap(encryptedMesssageBytes);
		
		byte ivLength =  encryptedMessageByteBuffer.get();
		if(ivLength != 16) {
			throw new IllegalArgumentException("Incorrect IV length");
		}
		
		byte[] ivBytes = new byte[ivLength];
		encryptedMessageByteBuffer.get(ivBytes);
		
		byte[] encryptedBytes = new byte[encryptedMessageByteBuffer.remaining()];
		encryptedMessageByteBuffer.get(encryptedBytes);
		
		SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		
		
		String decryptedText = new String(decryptedBytes, "UTF-8");
		
		return decryptedText;
	}

	private byte[] generateIvBytes() {
		byte[] ivBytes = new byte[128 / 8]; // 128 bit iv

		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(ivBytes);

		return ivBytes;
	}
	
	private byte[] generateSecretKeyBytes() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}
	
}

/* 
--- Output ---
-----------------------------------------------------------------
Algoritham 	: AES with CBC (Cipher Block Chaining) block mode
Key Length 	: 128 bits
-----------------------------------------------------------------
Plain Text 	: read2rakesh
Encrypted Text 	: EClGh792b1NPLW3e4I0mK63R24vRSU4Rk7SctctyCCoQ
Decrypted Text 	: read2rakesh


Encrypted same text multiple times
Plain Text : read2rakesh | Encrypted Text : EAbXA5AU+pvJ1tfHQlT68/9uMa4Pq7El5mtSSVzoGlXE
Plain Text : read2rakesh | Encrypted Text : EA5RSzFWsIA8OyrThRWTqw2zLuMvc+yt2VG8hwWdS829
Plain Text : read2rakesh | Encrypted Text : ENnZSPIq4OgwEkaRhhy5f8pbeswrxo3N7w4MFk0Js8RP
*/