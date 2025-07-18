package com.jps.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @className: OrderPartDo
 * @author: zf
 * @date: 2025/4/23 15:33
 * @version: 1.0
 * @description: 制单部位表
 */
@Data
@TableName("order_part")
public class OrderPartDo extends BaseDo {

	/* id */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/** 制单id */
	private Long workOrderId;

	/** 编号 */
	private String no;

	/** 部位名称 */
	private String partName;

	/** 误差范围 */

	@Schema(description = "误差范围")
	private String error;

	/** 基准尺寸 */
	private String basicSize;

	/** 码差 */
	private String sizeDifference;

	/** 缩率 */
	private String shrinkage;

	/** 度量方法 */
	private String dimMethod;

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
//	@TableLogic
//	@TableField(fill = FieldFill.INSERT)
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
