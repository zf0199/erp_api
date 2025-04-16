package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.java.Log;

/**
 * @className: OrderColorDo
 * @author: zf
 * @date: 2025/4/15 14:35
 * @version: 1.0
 * @description:
 */

@Data
@TableName("order_color")
public class OrderColorDo {

	@Schema(description = "制单颜色id")
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	@Schema(description = "制单id")
	private Long workOrderId;

	@Schema(description = "颜色编号")
	private String no;

	@Schema(description = "组号")
	private String groupNo;

	@Schema(description = "颜色代码")
	private String code;

	@Schema(description = "颜色名称")
	private String name;

}
