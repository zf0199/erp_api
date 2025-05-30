package com.jps.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.java.Log;

import java.time.LocalDateTime;

/**
 * @className: OrderColorDo
 * @author: zf
 * @date: 2025/4/15 14:35
 * @version: 1.0
 * @description:
 */

@Data
@TableName("order_color")
public class OrderColorDo {

	@Schema(description = "制单颜色id")
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	@Schema(description = "制单id")
	private Long workOrderId;

	@Schema(description = "颜色编号")
	private String no;

	@Schema(description = "组号")
	private String groupNo;

	@Schema(description = "颜色代码")
	private String code;

	@Schema(description = "颜色名称")
	private String name;

	@Schema(description = "合计")
	private Integer sum;

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
	 * 逻辑删除标识 (0未删除, 1已删除)
	 */
	@Schema(description="逻辑删除标识 (0未删除, 1已删除)")

	private Integer delFlag;


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
