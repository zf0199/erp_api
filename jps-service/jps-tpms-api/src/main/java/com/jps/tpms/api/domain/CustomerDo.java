package com.jps.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户表
 *
 * @author pig
 * @date 2025-03-17 15:59:23
 */
@Data
@TableName("customer")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "客户表")
public class CustomerDo extends BaseDo {


	/**
	* 客户id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="客户id")
    private Long id;

	/**
	* 客户类型
	*/
    @Schema(description="客户类型")
    private String type;

	/**
	* 客户代号
	*/
    @Schema(description="客户代号")
    private String no;

	/**
	* 客户名称
	*/
    @Schema(description="客户名称")
    private String name;

	/**
	* 所属部门
	*/
    @Schema(description="所属部门")
    private Long fromDepart;

	/**
	* 联系电话
	*/
    @Schema(description="联系电话")
    private String tel;

	/**
	* 联系人
	*/
    @Schema(description="联系人")
    private String leader;

	/**
	* 职位
	*/
    @Schema(description="职位")
    private String job;

	/**
	* 地址
	*/
    @Schema(description="地址")
    private String addr;

	/**
	* 收货地址
	*/
    @Schema(description="收货地址")
    private String stockAddr;

	/**
	* 所属组织
	*/
    @Schema(description="所属组织")
    private Long org;


}