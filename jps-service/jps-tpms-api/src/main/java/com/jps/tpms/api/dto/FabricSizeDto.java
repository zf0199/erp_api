package com.jps.tpms.api.dto;

import lombok.Data;

/**
 * @className: FabricSizeDto
 * @author: zf
 * @date: 2025/4/19 11:06
 * @version: 1.0
 * @description:
 */
@Data
public class FabricSizeDto {


	private Long orderColorId;

	/**
	 *  制单尺码
	 */
	private String orderSize;


	private String orderSizeNum;
	/**
	 *   下单尺寸序号
	 */
	private Integer orderSort;

	/**
	 *  物料尺码
	 */
	private String fabricSize;

	/**
	 *  尺码单位用量
	 */
	private String sizeUnitUsage;

	/**
	 *   尺码损耗
	 */
	private String  sizeLossRatio;


}
