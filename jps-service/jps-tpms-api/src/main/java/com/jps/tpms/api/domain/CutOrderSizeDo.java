package com.jps.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @className: CutOrderSizeDo
 * @author: zf
 * @date: 2025/4/28 14:33
 * @version: 1.0
 * @description:
 */

/**
 *  裁床单尺码表
 */
@Data
@TableName("cut_order_size")
public class CutOrderSizeDo {

	/**
	 *  id
	 */
	@TableId(type = IdType.ASSIGN_ID)
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
	private Integer sizeSumNum;

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
	private Integer bundleNum;

	/**
	 *   下单数量
	 */
	private Integer orderSizeNum;

	/**
	 * 备注
	 */
	@Schema(description="备注")
	private String remark;


	/**
	 * 状态 (0启用 1禁用)
	 */
	@Schema(description="状态 (0启用 1禁用)")
	@TableField(fill = FieldFill.INSERT)
	private Integer status;


	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description="创建人")
	private String createBy;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description="创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改人
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@Schema(description="修改人")
	private String updateBy;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@Schema(description="更新时间")
	private LocalDateTime updateTime;
}
