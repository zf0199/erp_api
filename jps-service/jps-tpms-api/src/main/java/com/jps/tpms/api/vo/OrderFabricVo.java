package com.jps.tpms.api.vo;

import com.jps.tpms.api.domain.OrderFabricColorSizeDo;
import com.jps.tpms.api.domain.OrderFabricDo;
import lombok.Data;

import java.util.List;

/**
 * @className: OrderFabricVo
 * @author: zf
 * @date: 2025/4/19 11:31
 * @version: 1.0
 * @description:
 */
@Data
public class OrderFabricVo extends OrderFabricDo {

	private List<OrderFabricColorSizeDo> orderFabricColorSizeDos;
}
