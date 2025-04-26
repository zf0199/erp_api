package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 物料表
 *
 * @author pig
 * @date 2025-03-20 19:58:48
 */
@Data
@TableName("material")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "物料表")
public class MaterialDo extends BaseDo{

 
	/**
	* id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="id")
    private Long id;

	/**
	* 物料名称
	*/
    @Schema(description="物料名称")
    private String name;

	/**
	* 物料编码
	*/
    @Schema(description="物料编码")
    private String no;

	/**
	* 采购单位
	*/
    @Schema(description="采购单位")
    private Long unit;



	/**
	* 所属组织
	*/
    @Schema(description="所属组织")
    private Long org;

	/**
	* 物料大类
	*/
    @Schema(description="物料大类")
    private Long materialTypeId;


	/**
	 *  物料品类
	 */
	private Integer materialCategory;

	/**
	 *  图样
	 */
	private String photo;

	/**
	* 物料颜色
	*/
    @Schema(description="物料颜色")
    private Long color;

	/**
	* 规格成分
	*/
    @Schema(description="规格成分")
    private String composition;

	/**
	* 布封(宽度)
	*/
    @Schema(description="布封(宽度)")
    private String width;

	/**
	* 转换率
	*/
    @Schema(description="转换率")
    private Integer fx;

	/**
	* 单价
	*/
    @Schema(description="单价")
    private Integer price;

	/**
	* 供应商
	*/
    @Schema(description="供应商")
    private Long supplier;

	/**
	* 是否配色
	*/
    @Schema(description="是否配色")
    private Integer isColorScheme;

	/**
	* 是否配码
	*/
    @Schema(description="是否配码")
    private Integer isMatchingSize;

	/**
	* 克重
	*/
    @Schema(description="克重")
    private String weight;

}
