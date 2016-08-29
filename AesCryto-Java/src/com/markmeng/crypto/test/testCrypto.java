package com.markmeng.crypto.test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import com.markmeng.crypto.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class testCrypto {

	@Test
	public void testAES() {
		String key = "password";
		String[] plaintexts = { "SYSTSTKBP", "", " ", "S1234568D", "T5000045H",
				"This is @ test @ccount!" };
		String iv = "RandomInitVector"; // LENGTH MUST BE 16
		String ciphertext = null;
		for (int i = 0; i < plaintexts.length; i++) {
			String plaintext = plaintexts[i];
			String decryptString = "";
			try {
				byte[] ciphertextBytes = AESCrypto.encrypt(key, iv, plaintext);
				ciphertext = DatatypeConverter.printHexBinary(ciphertextBytes);
				System.out.println("Encryption: " + plaintext + " >> " + ciphertext);
			} catch (BadPaddingException e) {
				System.out.println("Encryption Failed");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				byte[] decryptStringByte = AESCrypto.decrypt(key, iv, ciphertext);
				decryptString = new String(decryptStringByte, Utility.ENCODING);
				System.out.println("Decryption: " + ciphertext + " >> " + decryptString + "\n\t");
			} catch (BadPaddingException e) {
				System.out.println("Decryption Failed");
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals("Decrypted text should be equal to the plaintext", decryptString,
					plaintext);
		}
		System.out.println("\nAES TESTING FINISHED");
	}

	@Test
	public void testDES() {
		try {
			KeyGenerator keyGenerator;
			keyGenerator = KeyGenerator.getInstance("DES");

			keyGenerator.init(new SecureRandom());
			SecretKey secretKey = keyGenerator.generateKey();

			String[] plaintexts = { "SYSTSTKBP", "", " ", "S1234568D", "T5000045H",
					"This is @ test @ccount!" };
			String iv = "RandomInitVector"; // LENGTH MUST BE 16
			String ciphertext = null;
			for (int i = 0; i < plaintexts.length; i++) {
				String plaintext = plaintexts[i];
				String decryptString = "";
				try {
					byte[] ciphertextBytes = DESCrypto.encrypt("01234567".getBytes(), iv,
							plaintext);
					ciphertext = DatatypeConverter.printHexBinary(ciphertextBytes);
					System.out.println("Encryption: " + plaintext + " >> " + ciphertext);
				} catch (BadPaddingException e) {
					System.out.println("Encryption Failed");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					byte[] decryptStringByte = DESCrypto.decrypt("01234567".getBytes(), iv,
							ciphertext);
					decryptString = new String(decryptStringByte, Utility.ENCODING);
					System.out.println("Decryption: " + ciphertext + " >> " + decryptString
							+ "\n\t");
				} catch (BadPaddingException e) {
					System.out.println("Decryption Failed");
				} catch (Exception e) {
					e.printStackTrace();
				}
				assertEquals("Decrypted text should be equal to the plaintext", decryptString,
						plaintext);
			}
			System.out.println("\nDES TESTING FINISHED");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
