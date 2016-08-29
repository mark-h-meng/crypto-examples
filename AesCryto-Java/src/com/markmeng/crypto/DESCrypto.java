package com.markmeng.crypto;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class DESCrypto {
	public static byte[] encrypt(byte[] key, String initVector,
			String plaintext) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance(Utility.MD_SHA1);
		byte[] keyBytes = Arrays.copyOf(
				messageDigest.digest(key), 16);
		IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(Utility.ENCODING));
		SecretKey secretKey = new SecretKeySpec(keyBytes, "DES");
		Cipher cipher = Cipher.getInstance(Utility.DES_CIPHER_MODE);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] result = cipher.doFinal(plaintext.getBytes());
		return result;
	}

	public static byte[] decrypt(byte[] key, String initVector,
			String ciphertext) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance(Utility.MD_SHA1);
		byte[] keyBytes = Arrays.copyOf(
				messageDigest.digest(key), 16);
		IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(Utility.ENCODING));
		SecretKey secretKey = new SecretKeySpec(keyBytes, "DES");
		Cipher cipher = Cipher.getInstance(Utility.DES_CIPHER_MODE);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] ciphertextBytes = DatatypeConverter.parseHexBinary(ciphertext);
		byte[] result = cipher.doFinal(ciphertextBytes);
		return result;
	}
}
