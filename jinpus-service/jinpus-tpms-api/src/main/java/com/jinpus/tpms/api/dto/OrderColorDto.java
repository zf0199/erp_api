package com.jinpus.tpms.api.dto;

import com.jinpus.tpms.api.domain.OrderColorDo;
import com.jinpus.tpms.api.domain.OrderSizeDo;
import lombok.Data;

import java.util.List;

/**
 * @className: OrderColorDto
 * @author: zf
 * @date: 2025/4/15 15:24
 * @version: 1.0
 * @description:
 */
@Data
public class OrderColorDto extends OrderColorDo {

	private List<OrderSizeDo> orderSizes;
}
