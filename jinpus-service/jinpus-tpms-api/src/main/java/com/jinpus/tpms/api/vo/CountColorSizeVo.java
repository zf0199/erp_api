package com.jinpus.tpms.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: CountColorSizeVo
 * @author: zf
 * @date: 2025/4/30 14:17
 * @version: 1.0
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountColorSizeVo {


	/**
	 *   颜色
	 */
	private String color;

	/**
	 *   尺码
	 */
	private String size;

	/**
	 *  下单尺码
	 */
	private Integer orderNum;

	/**
	 *   裁床数量
	 */
	private Integer cutNum;

	/**
	 *  排序
	 */
	private Integer sort;
}
