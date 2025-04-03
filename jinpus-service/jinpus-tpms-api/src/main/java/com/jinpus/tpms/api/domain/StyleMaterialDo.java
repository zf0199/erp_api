package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 款类默认物料表
 *
 * @author pig
 * @date 2025-03-20 20:01:52
 */
@Data
@TableName("style_material")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "款类默认物料表")
public class StyleMaterialDo extends Model<StyleMaterialDo> {


	/**
	* 物料id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="物料id")
    private Long id;

	/**
	 *   款类id
	 */
	private Long styleId;

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
	* 物料类型id
	*/
    @Schema(description="物料类型id")
    private Long materialTypeId;

	@Schema(description="物料类型")
	private Integer type;

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
	* widht
	*/
    @Schema(description="widht")
    private String widht;

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
	* 物料分类
	*/
    @Schema(description="物料分类")
    private Long materialStyleId2;

	/**
	* 备注
	*/
    @Schema(description="备注")
    private String remake;

	/**
	* 设计单位
	*/
    @Schema(description="设计单位")
    private Integer designUnit;

	/**
	* 设计单位用量
	*/
    @Schema(description="设计单位用量")
    private String designQty;

	/**
	* 克重
	*/
    @Schema(description="克重")
    private String weight;

	/**
	* 逻辑删除标识 (0未删除, 1已删除)
	*/
    @Schema(description="逻辑删除标识 (0未删除, 1已删除)")
    private Integer isDel;

	/**
	* 状态 (0启用 1禁用)
	*/
    @Schema(description="状态 (0启用 1禁用)")
    private Integer status;

	/**
	* 创建人
	*/
	@TableField(fill = FieldFill.INSERT)
    @Schema(description="创建人")
    private String createBy;

	/**
	* 创建时间
	*/
	@TableField(fill = FieldFill.INSERT)
    @Schema(description="创建时间")
    private LocalDateTime createTime;

	/**
	* 修改人
	*/
	@TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description="修改人")
    private String updateBy;

	/**
	* 更新时间
	*/
	@TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description="更新时间")
    private LocalDateTime updateTime;
}
