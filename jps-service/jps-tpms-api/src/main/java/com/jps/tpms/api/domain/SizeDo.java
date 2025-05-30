package com.jps.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 尺码表
 *
 * @author pig
 * @date 2025-03-20 20:01:03
 */
@Data
@TableName("size")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "尺码表")
public class SizeDo extends Model<SizeDo> {


	/**
	* 尺码id
	*/
    @Schema(description="尺码id")
    private Long id;

	/**
	* 成衣尺寸
	*/
    @Schema(description="成衣尺寸")
    private String sizeNo;

	/**
	* 洗前尺寸
	*/
    @Schema(description="洗前尺寸")
    private String bSizeNo;

	/**
	* 成衣码值
	*/
    @Schema(description="成衣码值")
    private String sizeNoValue;

	/**
	* 洗前码值
	*/
    @Schema(description="洗前码值")
    private String bSizeNoValue;

	/**
	* 部位id
	*/
    @Schema(description="部位id")
    private Long workOrderMarkId;

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
