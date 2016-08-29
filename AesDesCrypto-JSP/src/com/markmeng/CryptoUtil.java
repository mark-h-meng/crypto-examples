package com.markmeng;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class CryptoUtil {
	public static String DEFAULT_ENCODING = "ISO-8859-1";
	public static String DEFAULT_PASSWORD_DIGEST = "SHA-1";
	public static String DEFAULT_CRYPTO_MODE = "AES/OFB8/NoPadding";
	public static String DEFAULT_IV = "RandomInitVector";

	/**
	 * Gets a User object of the specified user identification.
	 * 
	 * @param key
	 *            The secret of AES encryption.
	 * @param initVector
	 *            The initialization vector.
	 * @param plaintext
	 *            The plain text going to be encrypted.
	 * 
	 * @return String cipertext
	 */
	public static String encryptAES(String key, String initVector,
			String plaintext) throws Exception {
		MessageDigest messageDigest = MessageDigest
				.getInstance(DEFAULT_PASSWORD_DIGEST);
		byte[] keyBytes = Arrays.copyOf(
				messageDigest.digest(key.getBytes(DEFAULT_ENCODING)), 16);
		IvParameterSpec iv = new IvParameterSpec(
				initVector.getBytes(DEFAULT_ENCODING));
		SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
		Cipher cipher = Cipher.getInstance(DEFAULT_CRYPTO_MODE);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] result = cipher.doFinal(plaintext.getBytes());
		return DatatypeConverter.printHexBinary(result);
	}

	/**
	 * Gets a User object of the specified user identification.
	 * 
	 * @param key
	 *            The secret of AES decryption.
	 * @param initVector
	 *            The initialization vector.
	 * @param ciphertext
	 *            The cipher text going to be encrypted.
	 * 
	 * @return String plaintext
	 */
	public static String decryptAES(String key, String initVector,
			String ciphertext) throws Exception {
		MessageDigest messageDigest = MessageDigest
				.getInstance(DEFAULT_PASSWORD_DIGEST);
		byte[] keyBytes = Arrays.copyOf(
				messageDigest.digest(key.getBytes(DEFAULT_ENCODING)), 16);
		IvParameterSpec iv = new IvParameterSpec(
				initVector.getBytes(DEFAULT_ENCODING));
		SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
		Cipher cipher = Cipher.getInstance(DEFAULT_CRYPTO_MODE);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] ciphertextBytes = DatatypeConverter.parseHexBinary(ciphertext);
		byte[] result = cipher.doFinal(ciphertextBytes);
		return new String(result, DEFAULT_ENCODING);
	}
}

