package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @className: OrderFabricSizeDo
 * @author: zf
 * @date: 2025/4/17 11:12
 * @version: 1.0
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_fabric_size")
public class OrderFabricSizeDo {

	/**
	 * id
	 */
	private long id;

	/**
	 *  制单面料id
	 */
	private  Long orderFabricId;

	/**
	 * 面料尺码
	 */
	private String orderSize;

	/**
	 * 面料尺码
	 */
	private String fabricSize;

	/**
	 * 设计用量
	 */
	private String unitUsage;

	/**
	 * 损耗
	 */
	private String lossRatio;

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
