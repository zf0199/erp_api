package com.jinpus.tpms.api.dto;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @className: CutOrderSizeDto
 * @author: zf
 * @date: 2025/4/28 14:33
 * @version: 1.0
 * @description:
 */

/**
 *  裁床单尺码表
 */
@Data

public class CutOrderSizeDto {

	/**
	 *  id
	 */
	private Long id;

	/**
	 *  裁床单id
	 */
	private Long cutOrderId;

	/**
	 *  分床颜色id
	 */
	private Long cutColorId;

	private Long cutOrderBedId;

	/**
	 *   尺码名称
	 */
	private String sizeName;

	/**
	 *   尺码总数数量
	 */
	private String sizeSumNum;

	/**
	 *   尺码数量
	 */
	private Integer sizeNum;

	/**
	 *   序号
	 */
	private Integer sort;

	/**
	 *   扎号
	 */
	private String bundleNum;

	/**
	 *   下单数量
	 */
	private Integer orderSizeNum;

	/**
	 * 备注
	 */
	@Schema(description="备注")
	private String remark;

}
