package com.jinpus.tpms.api.domain;

/**
 * @className: CutOrderBundleDo
 * @author: zf
 * @date: 2025/4/28 14:05
 * @version: 1.0
 * @description:
 */

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *   裁床分床表
 */
@Data
@TableName("cut_order_bed")
public class CutOrderBedDo {

	/**
	 *   id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 *  制单id
	 */
	private Long workOrderId;

	/**
	 *   裁床单id
	 */
	private Long cutOrderId;

	/**
	 *   床号
	 */
	private Integer bedNum;

	/**
	 *   编号
	 */
	private String  no;

	/**
	 *   裁床类型
	 */
	private String type;

	/**
	 *   裁床数量
	 */
	private Integer num;


	/**
	 *   裁剪人
	 */
	private String cutBy;

	/**
	 *   部位
	 */
	private String cutPart;

	/**
	 *   月床号
	 */
	private String monthCutBed;

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
