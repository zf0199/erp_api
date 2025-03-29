package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 生产制单基础表
 *
 * @author pig
 * @date 2025-03-20 20:02:51
 */
@Data
@TableName("work_order")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "生产制单基础表")
public class WorkOrderDo extends Model<WorkOrderDo> {

 
	/**
	* id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="id")
    private Long id;

	/**
	* 生产制单单号
	*/
    @Schema(description="生产制单单号")
    private String orderNo;

	/**
	* 款类id
	*/
    @Schema(description="款类id")
    private Long styleId;

	/**
	* 款号
	*/
    @Schema(description="款号")
    private String styleNo;

	/**
	* 客户款号
	*/
    @Schema(description="客户款号")
    private String custStyleNo;

	/**
	* 下单情况
	*/
    @Schema(description="下单情况")
    private Integer orderStatus;

	/**
	* 制单数量
	*/
    @Schema(description="制单数量")
    private String quantity;

	/**
	* 单位
	*/
    @Schema(description="单位")
    private Integer unit;

	/**
	* 单价
	*/
    @Schema(description="单价")
    private String price;

	/**
	* 币种
	*/
    @Schema(description="币种")
    private Integer currency;

	/**
	* 客户
	*/
    @Schema(description="客户")
    private Long customerId;

	/**
	* 客户po
	*/
    @Schema(description="客户po")
    private Long custPo;

	/**
	* 季节
	*/
    @Schema(description="季节")
    private Integer season;

	/**
	* 下单日期
	*/
    @Schema(description="下单日期")
    private LocalDateTime orderTime;

	/**
	* 工厂货期
	*/
    @Schema(description="工厂货期")
    private LocalDateTime factoryDate;

	/**
	* 开单人
	*/
    @Schema(description="开单人")
    private Long orderUser;

	/**
	* 跟单人
	*/
    @Schema(description="跟单人")
    private Long follower;

	/**
	* 备注
	*/
    @Schema(description="备注")
    private String remark;

	/**
	* 客户评语
	*/
    @Schema(description="客户评语")
    private String review;

	/**
	* 包装要求
	*/
    @Schema(description="包装要求")
    private String pack;

	/**
	* 制单类型(版单0 制单1)
	*/
    @Schema(description="制单类型(版单0 制单1)")
    private Integer orderType;

	/**
	* 逻辑删除标识 (0未删除, 1已删除)
	*/
    @Schema(description="逻辑删除标识 (0未删除, 1已删除)")
    private Integer isDel;

	/**
	* 状态 (0启用 1禁用)
	*/
    @Schema(description="状态 (0启用 1禁用)")
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
