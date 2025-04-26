package com.jinpus.tpms.api.dto;

import lombok.Data;

/**
 * @className: FabricColorDto
 * @author: zf
 * @date: 2025/4/19 11:05
 * @version: 1.0
 * @description:
 */
@Data
public class FabricColorDto {

	private Long orderColorId;


	/**
	 * 制单颜色编号
	 */
	private String orderColorNo;
	/**
	 *  下单颜色
	 */
	private String orderColor;

	/**
	 * 色组
	 */
	private String groupNo;

	/**
	 *   物料颜色代码
	 */
	private String fabricColorCode;
	/**
	 * 物料颜色
	 */

	private String fabricColor;

	/**
	 *  供应商色号
	 */
	private String supplierColorNo;

	/**
	 *  颜色单位用量
	 */
	private String colorUnitUsage;

	/**
	 *   颜色损耗
	 */
	private String  colorLossRatio;
}
