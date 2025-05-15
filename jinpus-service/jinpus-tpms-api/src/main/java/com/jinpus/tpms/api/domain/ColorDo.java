package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class ColorDo extends BaseDo {


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

}
