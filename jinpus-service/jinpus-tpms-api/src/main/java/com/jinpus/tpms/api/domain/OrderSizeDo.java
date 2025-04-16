package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className: OrderSizeDo
 * @author: zf
 * @date: 2025/4/15 9:44
 * @version: 1.0
 * @description:
 */
@Data
@TableName("order_size")
@EqualsAndHashCode()
@Schema(description = "制单尺码表")
public class OrderSizeDo   {


	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "id")
	private Long id;

	@Schema(description = "制单id")
	private Long orderColorId;

	@Schema(description = "尺码")
	private String sizeName;

	@Schema(description = "尺码数量")
	private String sizeNum;



}
