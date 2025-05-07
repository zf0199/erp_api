package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * @className: BaseDo
 * @author: zf
 * @date: 2025/4/15 16:33
 * @version: 1.0
 * @description:
 */
@Data
@FieldNameConstants
public class BaseDetailDo {



	/**
	 * 备注
	 */
	@Schema(description="备注")
	private String remark;


	/**
	 * 状态 (0启用 1禁用)
	 */
	@Schema(description="状态 (0启用 1禁用)")
	@TableField(fill = FieldFill.INSERT)
	private Integer status;

	/**
	 * 逻辑删除标识 (0未删除, 1已删除)
	 */
	@Schema(description="逻辑删除标识 (0未删除, 1已删除)")
	private Integer delFlag;


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
	 * 修改人
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@Schema(description="修改人")
	private String updateBy;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@Schema(description="更新时间")
	private LocalDateTime updateTime;


}
