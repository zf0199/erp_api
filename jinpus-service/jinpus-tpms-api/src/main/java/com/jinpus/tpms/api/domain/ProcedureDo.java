package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工序表
 *
 * @author pig
 * @date 2025-03-20 20:00:07
 */
@Data
@TableName("`procedure`")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "工序表")
public class ProcedureDo extends BaseDo {


	/**
	* 工序id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="工序id")
    private Long id;

	/**
	* 工序编号
	*/
    @Schema(description="工序编号")
    private String no;

	/**
	* 工序类型
	*/
    @Schema(description="工序类型")
    private Long procedureTypeId;

	/**
	* 工序名称
	*/
    @Schema(description="工序名称")
    private String name;


	@Schema(description = "外文名称")
	private String eName;

	/**
	* 工价/每件
	*/
    @Schema(description="工价/每件")
    private String price;

	/**
	* 补贴率
	*/
    @Schema(description="补贴率")
    private String subsidyRate;

	/**
	* 补贴金额
	*/
    @Schema(description="补贴金额")
    private String subsidyAmount;

	/**
	* 工位
	*/
    @Schema(description="工位")
    private String workStation;


}
