package com.jps.domain;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @className: ChannelExcelDto
 * @author: zf
 * @date: 2025/5/22 16:46
 * @version: 1.0
 * @description:
 */
@Data
public class ChannelExcelDto {


	/**
	 *   时间
	 */
	@ExcelProperty("价格修改日期")
	private String time;


	/**
	 *  款号
	 */
	@ExcelProperty("款号")
	private String styleNo;


	/**
	 *   唯品
	 */
	@ExcelProperty("唯品会")
	private String weiPin;


	/**
	 *   爱库存
	 */
	@ExcelProperty("爱库存")
	private String aKuCun;

	/**
	 *  抖音
	 */
	@ExcelProperty("抖音")
	private String douyin;

	/**
	 *   拼多多
	 */
	@ExcelProperty("拼多多")
	private String pinduoduo;

	@ExcelProperty("京东")
	private String jingdong;

	@ExcelProperty("天猫")
	private String tianmao;

	@ExcelProperty("淘宝")
	private String taobao;

	@ExcelProperty("微信视频号")
	private String weixin;

	@ExcelProperty("快手")
	private String kuaishou;

	@ExcelProperty("其他")
	private String qita;

}
