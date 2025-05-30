package com.jps.domain;

import cn.idev.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jps.common.mybatis.base.BaseDo;
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
public class ProductDo extends BaseDo {

	/**
	 *   id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 *  客款号
	 */
//	@ExcelProperty("大货款号")
	private String  customerStyleNo;

	/**
	 *  内部款号
	 */
//	@ExcelIgnore("诚博款号")
	private String  interStyleNo;

	/**
	 *  设计师
	 */
//	@ExcelProperty("设计师")
	private String  designerName;

	/**
	 *  一级类目
	 */
	private String  firstCategory;

	/**
	 *  二级类目
	 */
	private String  secondCategory;

	/**
	 *  颜色
	 */
	private String  color;

	/**
	 *  年度
	 */
	private String  year;

	/**
	 *  季节
	 */
	private String  season;

	/**
	 *  波段
	 */
//	@TableField("`band`")
	private String waveBand;

	/**
	 *  是否有视觉图
	 */
	private String  isVisualImage;


	/**
	 *  商品状态
	 */
	private String  productStatus;


	/**
	 *  商品定位
	 */
	private String  productLevel;


	/**
	 *  商品卖点
	 */
	private String  sellingPoints;

	/**
	 *   商品图片
	 */
	private String photo1;

	/**
	 *  商品图片
	 */
	private String photo2;




}
