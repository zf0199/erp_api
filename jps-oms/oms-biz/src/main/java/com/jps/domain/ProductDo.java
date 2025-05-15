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
@TableName("product")
public class ProductDo {


	/**
	 *   id
	 */

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 *   
	 */
	private String  customerStyleNo;

	private String  interStyleNo;

	private String  designerName;

	private String  firstCategory;

	private String  secondCategory;

	private String  color;

	private String  year;

	private String  season;

	private String  band;

	private String  isVisualImage;

	private String  productStatus;

	private String  productLevel;

	private String  sellingPoints;

	private String  remark;


}
