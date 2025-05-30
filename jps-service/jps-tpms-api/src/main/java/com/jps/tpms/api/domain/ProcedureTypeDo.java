package com.jps.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生产工序类别表
 *
 * @author pig
 * @date 2025-03-20 20:00:24
 */
@Data
@TableName("procedure_type")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "生产工序类别表")
public class ProcedureTypeDo extends BaseDo {

 
	/**
	* id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="id")
    private Long id;

	/**
	* 工序类别
	*/
    @Schema(description="类别名称")
    private String name;

	/**
	* 工序编号
	*/
    @Schema(description="工序编号")
    private String no;


}
