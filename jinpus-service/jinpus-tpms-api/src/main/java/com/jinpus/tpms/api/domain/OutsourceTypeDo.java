package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 外发类型
 *
 * @author pig
 * @date 2025-04-08 15:31:25
 */
@Data
@TableName("outsource_type")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "外发类型")
public class OutsourceTypeDo extends Model<OutsourceTypeDo> {


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

	@Schema(description = "类型名称")
	private String typeName;

	/**
	* 备注
	*/
    @Schema(description="备注")
    private String remark;

	/**
	* 逻辑删除标识 (0未删除, 1已删除)
	*/
    @Schema(description="逻辑删除标识 (0未删除, 1已删除)")
    private Integer isDel;

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
