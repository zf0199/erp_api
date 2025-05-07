package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @className: OrderProcedureDetailDo
 * @author: zf
 * @date: 2025/5/6 11:36
 * @version: 1.0
 * @description: 工序详情表
 */
@Data
@TableName("order_procedure_detail")
public class OrderProcedureDetailDo extends BaseDetailDo {


	/**
	 * ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 制单工序id
	 */
	private Long orderProcedureId;

	/**
	 *  工序类型id
	 */
	private Long procedureTypeId;

	/**
	 * 工序编号
	 */
	private String no;

	/**
	 * 工序名称
	 */
	private String name;

	/**
	 * 工价
	 */
	private BigDecimal price;
	/**
	 * 补贴率
	 */
	private String subsidyRate;
	/**
	 * 补贴金额
	 */
	private String subsidyAmount;
	/**
	 * 工位
	 */
	private String workStation;


}
