package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 款类表
 *
 * @author pig
 * @date 2025-03-20 20:01:21
 */
@Data
@TableName("style")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "款类表")
public class StyleDo extends Model<StyleDo> {


	/**
	* 款类id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="款类id")
    private Long Id;

	/**
	* 款类代号
	*/
    @Schema(description="款类代号")
    private String no;

	/**
	* 款类名称
	*/
    @Schema(description="款类名称")
    private String name;
 
	/**
	* enName
	*/
    @Schema(description="enName")
    private String eName;

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
