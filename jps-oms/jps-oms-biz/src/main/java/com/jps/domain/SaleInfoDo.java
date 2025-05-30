package com.jps.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jps.common.mybatis.base.BaseDo;
import lombok.Data;

/**
 * @className: saleInfoDo
 * @author: zf
 * @date: 2025/5/17 11:31
 * @version: 1.0
 * @description:
 */
@Data
@TableName("sale_info")
public class SaleInfoDo  extends BaseDo {
	/**
	 *   id
	 */

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 *   商品id
	 */
	private Long productId;
}
