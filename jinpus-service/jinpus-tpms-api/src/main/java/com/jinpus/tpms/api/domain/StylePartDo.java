package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 款类部位表
 *
 * @author pig
 * @date 2025-03-20 20:02:11
 */
@Data
@TableName("style_part")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "款类部位表")
public class StylePartDo extends BaseDo {


	/**
	* id
	*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description="id")
    private Long id;

	/**
	 *   款类id
	 */
	private Long styleId;

	/**
	* 序号
	*/
    @Schema(description="序号")
    private String no;

	/**
	* 部位编号
	*/
    @Schema(description="部位编号")
    private String partNo;

	/**
	* 部位名称
	*/
    @Schema(description="部位名称")
    private String part;

	/**
	* 公差
	*/
    @Schema(description="公差")
    private String tolerance;

	/**
	* 度法
	*/
    @Schema(description="度法")
    private String dimenMethod;

	/**
	* 基准尺寸
	*/
    @Schema(description="基准尺寸")
    private String standardSize;




}
