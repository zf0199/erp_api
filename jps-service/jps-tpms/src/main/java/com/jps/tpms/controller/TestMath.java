package com.jps.tpms.controller;


import com.jps.tpms.api.domain.UnitDo;
import org.apache.commons.math3.fraction.Fraction;

import java.net.MalformedURLException;

/**
 * @className: TestMath
 * @author: zf
 * @date: 2025/4/24 9:02
 * @version: 1.0
 * @description:
 */
public class TestMath {

	public static void main(String[] args) throws MalformedURLException {

		var test = new UnitDo();

		System.out.println(test);

		Fraction f1 = new Fraction(2, 3);  // 2/3
		Fraction f2 = new Fraction(3, 4);  // 3/4

		Fraction sum = f1.add(f2);         // 2/3 + 3/4 = 17/12

		// 转为带分数
		int wholePart = sum.intValue();                    // 整数部分
		int numerator = sum.getNumerator() - wholePart * sum.getDenominator();  // 真分数的分子
		int denominator = sum.getDenominator();            // 分母

		String result;
		if (numerator == 0) {
			result = String.valueOf(wholePart);            // 没有余数，纯整数
		} else if (wholePart == 0) {
			result = numerator + "/" + denominator;        // 没有整数部分
		} else {
			result = wholePart + " " + numerator + "/" + denominator;
		}

		System.out.println("带分数格式: " + result);

	}

	/**
	 * 对两个分数进行加法运算，并返回带分数格式的字符串（如 1 1/4）
	 *
	 * @param f1 第一个分数
	 * @param f2 第二个分数
	 * @return 加法结果的带分数格式字符串
	 */
	public static String addAndFormatMixedFraction(Fraction f1, Fraction f2) {
		if (f1 == null || f2 == null) return "";

		Fraction sum = f1.add(f2);

		int wholePart = sum.intValue();
		int numerator = sum.getNumerator() - wholePart * sum.getDenominator();
		int denominator = sum.getDenominator();

		if (numerator == 0) {
			return String.valueOf(wholePart); // 纯整数
		} else if (wholePart == 0) {
			return numerator + "/" + denominator; // 纯分数
		} else {
			return wholePart + " " + numerator + "/" + denominator; // 带分数
		}
	}


	public record Test2(String name,int age){}
}
