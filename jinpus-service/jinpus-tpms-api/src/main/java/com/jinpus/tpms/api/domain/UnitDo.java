package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 单位表
 *
 * @author pig
 * @date 2025-03-17 16:19:20
 */
@Data
@TableName("unit")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "单位表")
public class UnitDo extends Model<UnitDo> {


	/**
	* id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="id")
    private Long id;

	/**
	* 单位名称
	*/
    @Schema(description="单位名称")
    private String unitName;

	/**
	* 单位类型
	*/
    @Schema(description="单位类型")
    private String unitType;

	/**
	* 缩写
	*/
    @Schema(description="缩写")
    private String abbr;

	/**
	* 描述
	*/
    @Schema(description="描述")
	@TableField("`desc`")
    private String desc;

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
	* 更新人
	*/
	@TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description="更新人")
    private LocalDateTime updateBy;

	/**
	* 更新时间
	*/
	@TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description="更新时间")
    private LocalDateTime updateTime;
}