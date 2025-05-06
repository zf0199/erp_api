package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @className: CutOrderColor
 * @author: zf
 * @date: 2025/4/28 11:38
 * @version: 1.0
 * @description:
 */

/**
 *   裁床颜色
 */
@Data
@TableName("cut_order_color")
public class CutOrderColorDo {

	/**
	 *   id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;


	/**
	 *  裁床单id
	 */
	private Long cutOrderId;

	/**
	 *   床号id
	 */
	private Long cutOrderBedId;

	/**
	 *  颜色
	 */
	private String  colorName;

	/**
	 *  层数
	 */
	private Integer layerNum;

	/**
	 *   颜色编码
	 */
	private String colorNo;

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
