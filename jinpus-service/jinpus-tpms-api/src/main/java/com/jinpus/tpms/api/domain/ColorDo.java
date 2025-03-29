package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 颜色表
 *
 * @author pig
 * @date 2025-03-26 11:39:37
 */
@Data
@TableName("color")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "颜色表")
public class ColorDo extends Model<ColorDo> {


	/**
	* id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="id")
    private Long id;

	/**
	* 编号
	*/
    @Schema(description="编号")
    private String no;

	/**
	* 名称
	*/
    @Schema(description="名称")
    private String name;

	/**
	 *  外文名称
	 */
	@Schema(description="外文名称")
    private String eName;

	@Schema(description = "颜色代号")
    private String code;



	/**
	* 颜色组
	*/
    @Schema(description="颜色组")
    private Long colorGroupId;

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
