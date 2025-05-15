package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 物料类型表
 *
 * @author pig
 * @date 2025-03-20 19:59:10
 */
@Data
@TableName("material_type")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "物料类型表")
public class MaterialTypeDo extends BaseDo{

 
	/**
	* id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="id")
    private Long id;
 
	/**
	* name
	*/
    @Schema(description="name")
    private String name;


	@Schema(description = "类型代码")
	private String typeCode;


	@Schema(description= "物料品类")
	private Integer materialCategory;



}
