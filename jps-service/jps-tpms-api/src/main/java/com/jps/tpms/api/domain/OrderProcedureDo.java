package com.jps.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @className: OrderProcedure
 * @author: zf
 * @date: 2025/5/5 17:23
 * @version: 1.0
 * @description:
 */

@Data
@TableName("order_procedure")
public class OrderProcedureDo  extends BaseDo{


	/**
	 *   id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long  id;

	/**
	 *   制单id
	 */
	private Long workOrderId;

	/**
	 *   裁床单id
	 */
	private Long cutOrderId;

	/**
	 *   款类id
	 */
	private Long styleId;

	/**
	 *   数量
	 */
	private Integer orderNum;

	/**
	 *   工序数量
	 */
	private Integer procedureNum;


	/**
	 *    工序总价
	 */
	private BigDecimal procedurePrice;

	/**
	 *  工序同步时间
	 */
	private  String procedureTime;


	/**
	 *   裁数
	 */
	private  Integer cutNum;


	/**
	 * 床数
	 */
	private  Integer bedNum;


	/**
	 * 裁剪比例
	 */
	private  String cutRatio;

	/**
	 *   扫菲完成率
	 */
	private String scanRatio;

}
