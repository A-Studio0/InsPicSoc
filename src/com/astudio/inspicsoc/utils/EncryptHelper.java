package com.astudio.inspicsoc.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * This helper class supports various of encrypt methods
 * 
 * @author NaOH
 * @version 1.0
 */
public class EncryptHelper {

	/**
	 * Encrypt the given string by MD5 arithmetic.
	 * 
	 * @param data
	 * @return
	 */
	public static String md5(String data) {
		String result = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(data.getBytes("ISO-8859-1"));
			byte bytes[] = md5.digest();
			for (int i = 0; i < bytes.length; i++) {
				result += Integer.toHexString(
						(0x000000ff & bytes[i]) | 0xffffff00).substring(6);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	private static String SECRET_KEY = "B@4f9fdc&1ETCde";

	private static byte[] MINI_ENCRYPT_KEY = "a1k".getBytes();

	public static final String miniEncrypt(String params) {
		return EncryptHelper.encrypt3DES(params, MINI_ENCRYPT_KEY);
	}

	public static final String miniDecrypt(String params) {
		return EncryptHelper.decrypt3DES(params, MINI_ENCRYPT_KEY);
	}

	/**
	 * Encrypt the given string with the given key in DES-3 arithmetic.
	 * 
	 * @param data
	 *            the given data to be encrypted
	 * @param key
	 *            the given key to encrypt the given data
	 * @return
	 */
	public static String encrypt3DES(String data, byte[] key) {
		if (data == null || key == null)
			return data;

		try {
			byte[] enData = doCrypt("DESede", Cipher.ENCRYPT_MODE, key,
					data.getBytes("UTF-16LE"));
			return byte2hex(enData);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Encrypt the given string with the default secret key in DES-3 arithmetic.
	 * 
	 * @param data
	 *            the given data to be encrypted
	 * @return
	 */
	public static String encrypt3DES(String data) {
		try {
			return encrypt3DES(data, SECRET_KEY.getBytes("ISO-8859-1"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Decrypt the given string with the given key in DES-3 arithmetic.
	 * 
	 * @param data
	 *            the given data to be decrypted
	 * @param key
	 *            the given key to decrypt the given data
	 * @return
	 */
	public static String decrypt3DES(String data, byte[] key) {
		if (data == null || key == null)
			return data;

		try {
			byte[] enData = hex2byte(data);
			byte[] deData = doCrypt("DESede", Cipher.DECRYPT_MODE, key, enData);
			return new String(deData, "UTF-16LE");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Decrypt the given string with the default secret key in DES-3 arithmetic.
	 * 
	 * @param data
	 *            the given data to be decrypted
	 * @return
	 */
	public static String decrypt3DES(String data) {
		try {
			return decrypt3DES(data, SECRET_KEY.getBytes("ISO-8859-1"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Private Methods ---------------------------------------------------------
	private static byte[] doCrypt(String algorithm, int mode, byte[] key,
			byte[] data) {

		byte[] result = new byte[] {};
		try {
			byte[] md5Key = md5(new String(key, "ISO-8859-1")).substring(0, 24)
					.getBytes("ISO-8859-1");
			SecretKey deskey = new javax.crypto.spec.SecretKeySpec(md5Key,
					algorithm);
			Cipher c1 = Cipher.getInstance(algorithm);
			c1.init(mode, deskey);
			result = c1.doFinal(data);
		} catch (UnsupportedEncodingException e) {
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 字节码转换成16进制字符串
	 * 
	 * @param byte[] b 输入要转换的字节码
	 * @return String 返回转换后的16进制字符串
	 */
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + "";
		}
		return hs.toUpperCase();
	}

	/**
	 * 根据给定的16进制组合的数字串，转化成一个二进制流串
	 * 
	 * @param s
	 * @return
	 */
	private static byte[] hex2byte(String s) {

		int length = s.length() / 2;
		byte[] bs = new byte[length];

		for (int i = 0; i < length; i++) {

			String substr = s.substring(i * 2, (i + 1) * 2);
			bs[i] = (byte) Integer.parseInt(substr, 16);
		}
		return bs;
	}

}
