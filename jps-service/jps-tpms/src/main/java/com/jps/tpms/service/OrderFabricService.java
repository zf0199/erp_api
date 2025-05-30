package com.jps.tpms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jps.tpms.api.domain.OrderFabricDo;
import com.jps.tpms.api.dto.OrderFabricDto;
import com.jps.tpms.api.vo.FabricColorSizeVo;

/**
 * @className: OrderFabricService
 * @author: zf
 * @date: 2025/4/22 11:05
 * @version: 1.0
 * @description:
 */
public interface OrderFabricService  extends IService<OrderFabricDo> {


	void deleteById(Long id);

	public void updateFabricOne(OrderFabricDto orderFabric);

	public FabricColorSizeVo selectFabricColorAndSize(String tag, Long workOrderId, Long orderFabricId);


}
