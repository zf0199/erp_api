package com.jps.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @className: ProdctDo
 * @author: zf
 * @date: 2025/5/14 14:32
 * @version: 1.0
 * @description:
 */

@Data
public class ProductVo extends ProductDo {


	/**
	 *   售卖信息
	 */
	private SaleInfoVo saleInfoVo;

}
