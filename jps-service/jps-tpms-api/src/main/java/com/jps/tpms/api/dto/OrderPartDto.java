package com.jps.tpms.api.dto;

import com.jps.tpms.api.domain.OrderPartDo;
import com.jps.tpms.api.domain.OrderPartSizeDo;
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
