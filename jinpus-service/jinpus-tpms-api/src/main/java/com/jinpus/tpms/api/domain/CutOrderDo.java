package com.jinpus.tpms.api.domain;

/**
 * @className: CutOrderDo
 * @author: zf
 * @date: 2025/4/26 11:55
 * @version: 1.0
 * @description:
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *    裁床单基础表
 */
@TableName("cut_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CutOrderDo {

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 *   制单id
	 */
	private Long workOrderId;

	/**
	 *   裁床单编号
	 */
	private String cutOrderNo;

	/**
	 *    进度
	 */
	private Integer progress;
}
