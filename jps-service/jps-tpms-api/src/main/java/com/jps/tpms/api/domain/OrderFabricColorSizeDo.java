package com.jps.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @className: OrderFabricColorDo
 * @author: zf
 * @date: 2025/4/17 11:11
 * @version: 1.0
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_fabric_color_size")
public class OrderFabricColorSizeDo {

	/**
	 *  id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private  Long id;


	/**
	 *  制单面料id
	 */
	private Long orderFabricId;


	/**
	 *  制单id
	 */
	private Long workOrderId;


	/**
	 * 制单颜色编号
	 */
	private String orderColorNo;

	/**
	 *  制单颜色
	 */
	private String orderColor;


	/**
	 *   制单数量
	 */
	private String colorNum;


	/**
	 *  制单颜色代码
	 */
	private String orderColorCode;

	/**
	 *  尺码排序
	 */
	private Integer  orderSort;


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
	 *  制单尺码
	 */
	private String orderSize;


	private String orderSizeNum;

	/**
	 *  物料尺码
	 */
	private String fabricSize;



	/**
	 *  颜色单位用量
	 */
	private String colorUnitUsage;

	/**
	 *   颜色损耗
	 */
	private String  colorLossRatio;

	/**
	 *  尺码单位用量
	 */
	private String sizeUnitUsage;

	/**
	 *   尺码损耗
	 */
	private String  sizeLossRatio;

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
