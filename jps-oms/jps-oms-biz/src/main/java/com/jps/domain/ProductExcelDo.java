package com.jps.domain;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ContentStyle;
import cn.idev.excel.enums.poi.HorizontalAlignmentEnum;
import cn.idev.excel.enums.poi.VerticalAlignmentEnum;
import com.pig4cloud.plugin.excel.annotation.ExcelLine;
import lombok.Data;

/**
 * @className: ProdctDo
 * @author: zf
 * @date: 2025/5/14 14:32
 * @version: 1.0
 * @description:
 */

@Data
//@ColumnWidth(60)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER, verticalAlignment = VerticalAlignmentEnum.CENTER)
public class ProductExcelDo {



	/**
	 * 导入时候回显行号
	 */
	@ExcelLine
	@ExcelIgnore
	private Long lineNum;
	/**
	 * 客款号
	 */
	@ExcelProperty("大货款号")
	private String customerStyleNo;

	/**
	 * 内部款号
	 */
	@ExcelProperty("诚博款号")
	private String interStyleNo;

	/**
	 * 设计师
	 */
	@ExcelProperty("设计师")
	private String designerName;

	/**
	 * 一级类目
	 */
	@ExcelProperty("大类目")
	private String firstCategory;

	/**
	 * 二级类目
	 */
	@ExcelProperty("二级类目")
	private String secondCategory;

	/**
	 * 颜色
	 */
	@ExcelProperty("颜色")
	private String color;

	/**
	 * 年度
	 */
	@ExcelProperty("年度")
	private String year;

	/**
	 * 季节
	 */
	@ExcelProperty("季度")
	private String season;

	/**
	 * 波段
	 */
	@ExcelProperty("波段")
//	@TableField("`band`")
	private String waveBand;

	/**
	 * 是否有视觉图
	 */
	@ExcelProperty("是否有视觉资料")
	private String isVisualImage;


	/**
	 * 商品状态
	 */
	@ExcelProperty("商品状态")
	private String productStatus;


	/**
	 * 商品定位
	 */
	@ExcelProperty("产品定位")
	private String productLevel;


	/**
	 * 商品卖点
	 */
	@ExcelProperty("销售卖点")
	private String sellingPoints;

	/**
	 * 商品图片
	 */
	@ExcelProperty("商品图片1")
	private String photo1;

	/**
	 * 商品图片
	 */
	@ExcelProperty("商品图片2")
	private byte[] photo2;

	@ExcelProperty("备注")
	private String remark;


}
