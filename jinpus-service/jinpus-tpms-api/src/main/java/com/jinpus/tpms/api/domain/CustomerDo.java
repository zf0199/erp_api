package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

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
public class CustomerDo extends Model<CustomerDo> {


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

	/**
	* 创建人
	*/
	@TableField(fill = FieldFill.INSERT)
    @Schema(description="创建人")
    private Long createBy;

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
    private Long updateBy;

	/**
	* 更新时间
	*/
	@TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description="更新时间")
    private LocalDateTime updateTime;

	@TableLogic(value = "0",delval = "1")
	@Schema(description = "是否删除 0未删除 1删除")
	private Integer isDelete;

	@Schema(description = "状态 0启用 1禁用")
	private Integer status;
}