package com.jinpus.tpms.util;

import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;

import static com.itextpdf.kernel.pdf.PdfName.Padding;

/**
 * @className: Base64Utils
 * @author: zf
 * @date: 2025/5/12 14:47
 * @version: 1.0
 * @description:
 */
public class Base64Utils {

	private static final String AES_ALGORITHM = "AES/CFB/NoPadding";

	// 指定 AES 加解密的模式和填充方式，CFB 模式和 NoPadding 填充方式
	private static final String AES_MODE = "AES/CFB/NoPadding";


	public static void main(String[] args) throws Exception {
		System.out.println(getLocalMacAddress());

		System.out.println(f1("123456"));
		String original = "social:social";

		System.out.println(toBase64(original));
		System.out.println(decryptBase64("nDMjfAgz", "1111111111111111"));
		System.out.println(encryptBase64("123456", "1111111111111111"));
	}



	public static String toBase64(String str) {
		return Base64.getEncoder().encodeToString(str.getBytes());
	}

	/**
	 * 解密 Base64 编码的密文
	 *
	 * @param base64CipherText Base64 编码后的密文
	 * @param key              密钥，必须是 16 字节
	 * @return 解密后的明文
	 * @throws Exception 解密过程中可能抛出的异常
	 */
	public static String decryptBase64(String base64CipherText, String key) throws Exception {
		// 创建 Cipher 实例，指定使用 AES/CFB/NoPadding 模式
		Cipher cipher = Cipher.getInstance(AES_MODE);

		// 创建密钥和初始化向量（IV）的参数，AES 需要 16 字节的密钥
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));

		// 初始化解密模式
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		// Base64 解码，得到原始的加密字节
		byte[] decodedBytes = Base64.getDecoder().decode(base64CipherText);

		// 执行解密操作
		byte[] result = cipher.doFinal(decodedBytes);

		// 将解密后的字节转回字符串并返回
		return new String(result, StandardCharsets.UTF_8);
	}

	/**
	 * 加密明文并返回 Base64 编码后的密文
	 *
	 * @param plainText 明文
	 * @param key       密钥，必须是 16 字节
	 * @return Base64 编码后的密文
	 * @throws Exception 加密过程中可能抛出的异常
	 */
	public static String encryptBase64(String plainText, String key) throws Exception {
		// 创建 Cipher 实例，指定使用 AES/CFB/NoPadding 模式
		Cipher cipher = Cipher.getInstance(AES_MODE);
		// 创建密钥和初始化向量（IV）的参数，AES 需要 16 字节的密钥
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
		// 初始化加密模式
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		// 执行加密操作，将明文转换为字节
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
		// 将加密后的字节转为 Base64 编码并返回
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	/**
	 * 加密明文，返回 Hex 编码密文
	 *
	 * @param plainText 明文
	 * @param key       密钥（16字节字符串）
	 */
	public static String encrypt(String plainText, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
		return bytesToHex(encrypted);
//		return encrypted;
	}

	/**
	 * 解密 Hex 编码密文，返回明文
	 *
	 * @param hexCipherText Hex 编码密文
	 * @param key           密钥（16字节字符串）
	 */
	public static String decrypt(String hexCipherText, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		byte[] decrypted = cipher.doFinal(hexToBytes(hexCipherText));
		return new String(decrypted, StandardCharsets.UTF_8);
	}

	// 工具方法：byte[] 转 hex
	private static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	// 工具方法：hex 转 byte[]
	private static byte[] hexToBytes(String hex) {
		int len = hex.length();
		byte[] bytes = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
					+ Character.digit(hex.charAt(i + 1), 16));
		}
		return bytes;
	}


	private static String f1(String password){
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(password);
	}



	public static String getLocalMacAddress() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface ni = networkInterfaces.nextElement();

				if (ni == null || ni.isLoopback() || ni.isVirtual() || !ni.isUp()) {
					continue; // 忽略回环、虚拟、未激活接口
				}

				byte[] macBytes = ni.getHardwareAddress();
				if (macBytes == null || macBytes.length == 0) {
					continue;
				}

				StringBuilder sb = new StringBuilder();
				for (byte b : macBytes) {
					sb.append(String.format("%02X-", b));
				}

				// 删除末尾多余的连字符
				if (sb.length() > 0) {
					sb.setLength(sb.length() - 1);
				}

				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "MAC 地址获取失败";
	}
}
