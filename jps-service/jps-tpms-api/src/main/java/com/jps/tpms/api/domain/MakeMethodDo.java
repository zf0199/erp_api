package com.jps.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 制作方法
 *
 * @author zf
 * @date 2025-03-20 19:58:04
 */
@Data
@TableName("make_method")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "制作方法")
public class MakeMethodDo extends Model<MakeMethodDo> {

 
	/**
	* id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="id")
    private Long id;

	/**
	* 制单表id
	*/
    @Schema(description="制单表id")
    private Long workOrderId;
 
	/**
	* beforeMethod
	*/
    @Schema(description="beforeMethod")
    private String beforeMethod;
 
	/**
	* afterMethod
	*/
    @Schema(description="afterMethod")
    private String afterMethod;
 
	/**
	* endMethod
	*/
    @Schema(description="endMethod")
    private String endMethod;
 
	/**
	* washMethod
	*/
    @Schema(description="washMethod")
    private String washMethod;

	/**
	* 印花方法
	*/
    @Schema(description="印花方法")
    private String patternMethod;

	/**
	* 绣花工艺
	*/
    @Schema(description="绣花工艺")
    private String embroideryMethod;

	/**
	* 裁剪工艺
	*/
    @Schema(description="裁剪工艺")
    private String cutMethod;

	/**
	* 是否删除 0已删除 1未删除
	*/
    @Schema(description="是否删除 0已删除 1未删除")
    private Integer isDel;

	/**
	* 状态 0启用 1禁用
	*/
    @Schema(description="状态 0启用 1禁用")
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
	* 更新人
	*/
	@TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description="更新人")
    private String updateBy;

	/**
	* 更新时间
	*/
	@TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description="更新时间")
    private LocalDateTime updateTime;
}
