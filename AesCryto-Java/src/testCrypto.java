import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class testCrypto {
	public static String encoding = "ISO-8859-1";

	public static void main(String[] args) {
		String key = "password";
		String[] plaintexts = { "SYSTSTKBP", "", " ", "S1234568D",
				"T5000045H", "This is @ test @ccount!" };
		String iv = "RandomInitVector"; // LENGTH MUST BE 16
		String ciphertext = null;
		for (int i = 0; i < plaintexts.length; i++) {
			String plaintext = plaintexts[i];
			String decryptString = "";
			try {
				byte[] ciphertextBytes = AESencrypt(key, iv, plaintext);
				ciphertext = DatatypeConverter.printHexBinary(ciphertextBytes);
				System.out.println("Encryption: " + plaintext + " >> "
						+ ciphertext);
			} catch (BadPaddingException e) {
				System.out.println("Encryption Failed");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				byte[] decryptStringByte = AESdecrypt(key, iv, ciphertext);
				decryptString = new String(decryptStringByte, encoding);
				System.out.println("Decryption: " + ciphertext + " >> "
						+ decryptString + "\n\t");
			} catch (BadPaddingException e) {
				System.out.println("Decryption Failed");
			} catch (Exception e) {
				e.printStackTrace();
			}
			String resultString = (decryptString.equalsIgnoreCase(plaintext))?"Passed":"Failed";
			System.out.println((i+1) +"/"+plaintexts.length+" "+resultString+"\n");
		}

	}

	private static byte[] AESencrypt(String key, String initVector,
			String plaintext) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
		byte[] keyBytes = Arrays.copyOf(
				messageDigest.digest(key.getBytes(encoding)), 16);
		IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(encoding));
		SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
		Cipher cipher = Cipher.getInstance("AES/OFB8/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] result = cipher.doFinal(plaintext.getBytes());
		return result;
	}

	private static byte[] AESdecrypt(String key, String initVector,
			String ciphertext) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
		byte[] keyBytes = Arrays.copyOf(
				messageDigest.digest(key.getBytes(encoding)), 16);
		IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(encoding));
		SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
		Cipher cipher = Cipher.getInstance("AES/OFB8/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] ciphertextBytes = DatatypeConverter.parseHexBinary(ciphertext);
		byte[] result = cipher.doFinal(ciphertextBytes);
		return result;
	}
}
