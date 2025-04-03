package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 款类默认工序表
 *
 * @author pig
 * @date 2025-03-27 15:31:24
 */
@Data
@TableName("style_procedure")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "款类默认工序表")
public class StyleProcedureDo extends Model<StyleProcedureDo> {


	/**
	* 款类工序id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="款类工序id")
    private Long id;

	/**
	 *   款类id
	 */
	private Long styleId;

	/**
	* 款类工序编号
	*/
    @Schema(description="款类工序编号")
    private String no;

	/**
	* 款类工序名称
	*/
    @Schema(description="款类工序名称")
    private String name;

	/**
	 *  款类工序类型(0,1,2,3)
	 */
	@Schema(description="款类工序类型")
	private Integer type;

	/**
	* 款类工价/每件
	*/
    @Schema(description="款类工价/每件")
    private String price;

	/**
	* 款类补贴率
	*/
    @Schema(description="款类补贴率")
    private String subsidyRate;

	/**
	* 款类补贴金额
	*/
    @Schema(description="款类补贴金额")
    private String subsidyAmount;


	/**
	 *   工时
	 */
	@Schema(description = "工时")
	private String workHours;



	/**
	* 备注
	*/
    @Schema(description="备注")
    private String remake;

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
	* 外文名称
	*/
    @Schema(description="外文名称")
    private String eName;

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
