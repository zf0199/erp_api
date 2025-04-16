package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 颜色组
 *
 * @author pig
 * @date 2025-03-25 19:58:27
 */
@Data
@TableName("color_group")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "颜色组")
public class ColorGroupDo extends BaseDo{

 
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
 
	/**
	* no
	*/
    @Schema(description="no")
    private String no;


}
