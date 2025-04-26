package com.jinpus.tpms.api.dto;

import com.jinpus.tpms.api.domain.OrderPartDo;
import com.jinpus.tpms.api.domain.OrderPartSizeDo;
import lombok.Data;

import java.util.List;

/**
 * @className: OrderPartDto
 * @author: zf
 * @date: 2025/4/23 16:01
 * @version: 1.0
 * @description:
 */
@Data
public class OrderPartDto  extends OrderPartDo {

	private List<OrderPartSizeDo> orderPartSize;
}
