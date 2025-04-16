package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 生产制单基础表
 *
 * @author pig
 * @date 2025-03-20 20:02:51
 */
@Data
@TableName("work_order")
@Schema(description = "生产制单基础表")
public class WorkOrderDo extends BaseDo  {

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
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description="下单日期")
    private LocalDateTime orderTime;

	/**
	* 工厂货期
	*/
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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


	@Schema(description = "前幅")
	private String frontPhoto;

	@Schema(description = "后幅")
	private String backPhoto;


}
