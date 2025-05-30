package com.jps.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @className: OrderProessDo
 * @author: zf
 * @date: 2025/4/23 16:55
 * @version: 1.0
 * @description:  制单工艺
 */
@TableName("order_process")
@Data
public class OrderProcessDo extends BaseDo {

	/* id */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 *   制单id
	 */
	private Long WorkOrderId;

	/* 工艺描述 */
	private String processDesc;

	/*  裁床工艺 */
	private String cuttingProcess;

	/* 车缝工艺 */
	private String sewingProcess;

	/* 后道工序 */
	private String postProcess;


}
