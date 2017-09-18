
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {
	public static byte[] AES_KEY = new byte[] { (byte) 110, (byte) 122,
			(byte) 177, (byte) 177, (byte) 123, (byte) 87, (byte) 76,
			(byte) 219, (byte) 114, (byte) 64, (byte) 193, (byte) 236,
			(byte) 127, (byte) 140, (byte) 160, (byte) 110 };

	public static byte[] AES_IV = new byte[] { (byte) 124, (byte) 47,
			(byte) 121, (byte) 98, (byte) 191, (byte) 192, (byte) 57,
			(byte) 188, (byte) 196, (byte) 208, (byte) 232, (byte) 71,
			(byte) 158, (byte) 25, (byte) 131, (byte) 99 };

	/** 算法/模式/填充 **/
	private static final String CipherMode = "AES/CBC/PKCS5Padding";

	public static String encryptParam(String paramString) {
		String resultStr = "";
		try {
			Cipher ecipher = Cipher.getInstance(CipherMode);
			ecipher.init(Cipher.ENCRYPT_MODE,
					new SecretKeySpec(AES_KEY, "AES"), new IvParameterSpec(
							AES_IV));
			byte[] result = ecipher.doFinal(paramString.getBytes("UTF-8"));
			// 将byte[]转换为base64
			resultStr = Base64.encode(result);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resultStr;
	}

	public static String decrypt(String cipherText) {
		try {
			Cipher dcipher = Cipher.getInstance(CipherMode);
			dcipher.init(Cipher.DECRYPT_MODE,
					new SecretKeySpec(AES_KEY, "AES"), new IvParameterSpec(
							AES_IV));
			byte[] result = Base64.decode(cipherText);
			return new String(dcipher.doFinal(result));
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return "";
	}

}