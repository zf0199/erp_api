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
 * @date 2025-03-20 19:59:45
 */
@Data
@TableName("order_fabric")
@EqualsAndHashCode
@Schema(description = "制单面料表")
public class OrderFabricDo  {

 
	/**
	* id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="id")
    private Long id;

	/**
	 *  制单id
	 */
	private Long workOrderId;

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
    private String unit;

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
	 *   物料类别
	 */
	private Integer   materialCategory;

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
    @Schema(description="是否配色 0是 1否")
    private Integer isColorScheme;

	/**
	* 是否配码
	*/
    @Schema(description="是否配码 0是 1否")
    private Integer isMatchingSize;



	@Schema(description = "制单部位")
	private String mark;

	/**
	* 克重
	*/
    @Schema(description="克重")
    private String weight;

	/**
	* 设计用量
	*/
    @Schema(description="设计用量")
    private String bomQty;

	/**
	 *  单位用量
	 */
	private String unitUsage;

	/**
	 *   损耗
	 */
	private String  lossRatio;

	/**
	 *  物料图片
	 */
	private String fabricPhoto;

	/**
	 * 备注
	 */
	@Schema(description="备注")
	private String remark;


	/**
	 * 状态 (0启用 1禁用)
	 */
	@Schema(description="状态 (0启用 1禁用)")
	@TableField(fill = FieldFill.INSERT)
	private Integer status;

	/**
	 * 逻辑删除标识 (0未删除, 1已删除)
	 */
	@Schema(description="逻辑删除标识 (0未删除, 1已删除)")

	private Integer delFlag;


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
