package com.jps.tpms.api.domain;

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
import jakarta.validation.constraints.NotBlank;
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
public class CutOrderDo extends BaseDo{

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 *   制单id
	 */
	private Long workOrderId;

	/**
	 *   裁床单编号
	 */
	@NotBlank(message = "裁床单号不能为null")
	private String cutOrderNo;

	/**
	 *    进度
	 */
	private Integer progress;
}
