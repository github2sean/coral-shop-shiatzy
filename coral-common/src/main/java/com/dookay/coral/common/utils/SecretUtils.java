package com.dookay.coral.common.utils;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * DES加解密
 * @author : kezhan
 * @since : 2016年12月23日
 * @version : v0.0.1
 */
public class SecretUtils {

	public SecretUtils() {
	}

	/**
	 * 数据加密，算法(DES)
	 * @param cryptData 解密数据
	 * @param key key
	 * @param charsetName 编码格式
	 * @return
	 * @throws Exception
	 * @author : kezhan	
	 * @since : 2016年12月25日
	 */
	public static byte[] encrypt(String encryptData, String key, String charsetName) throws Exception {
		
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return cipher.doFinal(encryptData.getBytes("UTF-8"));
	}

	/**
	 * 数据解密，算法(DES)
	 * @param encryptData
	 * @param key
	 * @param charsetName
	 * @return
	 * @author : kezhan	
	 * @throws NoSuchPaddingException 
	 * @throws Exception 
	 * @since : 2016年12月25日
	 */
	public static String decrypt(String decryptData, String key, String charsetName) throws Exception {
		
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		byte[] bytesrc = convertHexString(decryptData);
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] retByte = cipher.doFinal(bytesrc);

		return new String(retByte);
	}

	/**
	 * convertHexString
	 * @param string
	 * @return
	 * @author : kezhan	
	 * @since : 2016年12月25日
	 */
	public static byte[] convertHexString(String string) {
		byte digest[] = new byte[string.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = string.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	/**
	 * toHexString
	 * @param b
	 * @return
	 * @author : kezhan	
	 * @since : 2016年12月25日
	 */
	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}

	/**
	 * 测试
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		/*String key = "hello123";
		String text = "hello你好啊啊 啊 啊 啊啊啊  年三十你说呢1215454545";
		System.out.println("加密前的明文:" + text);

		String cryperText = "";
		try {
			cryperText = toHexString(encrypt(text, key, "UTF-8"));
			System.out.println("加密后的明文:" + cryperText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			System.out.println("解密后的明文:" + decrypt(cryperText, key, "UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}
}
