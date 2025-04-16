package com.jinpus.tpms.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * @className: Test
 * @author: zf
 * @date: 2025/3/28 14:38
 * @version: 1.0
 * @description:
 */
public class Test {

	public static void main(String[] args) {
		// 你的 Base64 字符串（建议从文件或接口读取，如果太长）
		String base64String = "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";

		// 输出文件路径
		String outputPath = "D:/output1.mrt"; // 可换成 .pdf、.png、.txt 等

		base64ToFile(base64String, outputPath);
	}

	public static void base64ToFile(String base64Str, String filePath) {
		try {
			// 如果包含类似 "data:application/pdf;base64," 这样的前缀，先去掉
			if (base64Str.contains(",")) {
				base64Str = base64Str.split(",")[1];
			}

			// 解码
			byte[] data = Base64.getDecoder().decode(base64Str);

			// 写入文件
			try (OutputStream stream = new FileOutputStream(filePath)) {
				stream.write(data);
				System.out.println("文件已保存为：" + filePath);
			}

		} catch (Exception e) {
			System.err.println("保存文件失败：" + e.getMessage());
		}
	}
}
