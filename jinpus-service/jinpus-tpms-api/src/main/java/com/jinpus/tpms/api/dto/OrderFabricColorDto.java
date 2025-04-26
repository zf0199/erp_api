package com.jinpus.tpms.api.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jinpus.tpms.api.domain.OrderFabricColorDo;
import com.jinpus.tpms.api.domain.OrderFabricSizeDo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @className: OrderFabricColorDo
 * @author: zf
 * @date: 2025/4/17 11:11
 * @version: 1.0
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_fabric_color")
public class OrderFabricColorDto extends OrderFabricColorDo {

	private List<OrderFabricSizeDo> orderFabricSizeDos;


}
