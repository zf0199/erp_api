package com.jps.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @className: CutOrderColorSizeDo
 * @author: zf
 * @date: 2025/4/28 14:48
 * @version: 1.0
 * @description:
 */
@Data
@TableName("cut_order_color_size")
public class CutOrderColorSizeDo {


	/**
	 *   id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 *   分床id
	 */
	private Long bedId;

	/**
	 *  床号
	 */
	private Integer bedNum;

	/**
	 *  扎号
	 */
	private Integer bundleNum;

	/**
	 *  颜色
	 */
	private String color;

	/**
	 *  面料颜色
	 */
	private String fabricColor;

	/**
	 *  尺码
	 */
	private String size;

	/**
	 *  尺码数量
	 */
	private Integer sizeNum;


}
