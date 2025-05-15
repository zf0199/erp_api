package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 制单部位表
 *
 * @author pig
 * @date 2025-03-20 20:03:14
 */
@Data
@TableName("work_order_mark")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "制单部位表")
public class WorkOrderMarkDo extends BaseDo{


	/**
	* 尺寸id
	*/
    @Schema(description="尺寸id")
    private Long id;

	/**
	* 部位编号
	*/
    @Schema(description="部位编号")
    private String no;

	/**
	* 部位名称
	*/
    @Schema(description="部位名称")
    private String name;

	/**
	* 制单id
	*/
    @Schema(description="制单id")
    private Long workOrderId;

	/**
	* 度法
	*/
    @Schema(description="度法")
    private String dimenMethod;

	/**
	* 误公差
	*/
    @Schema(description="误公差")
    private String tolerance;

	/**
	* 是否锁定
	*/
    @Schema(description="是否锁定")
    private Integer isLocak;

	/**
	* 备注
	*/
    @Schema(description="备注")
    private String remark;

	/**
	* 基准尺寸
	*/
    @Schema(description="基准尺寸")
    private String standardSize;


}
