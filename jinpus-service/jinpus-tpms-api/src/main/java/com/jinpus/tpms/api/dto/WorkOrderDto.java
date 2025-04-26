package com.jinpus.tpms.api.dto;

import com.jinpus.tpms.api.domain.OrderColorDo;
import com.jinpus.tpms.api.domain.OrderProcessDo;
import com.jinpus.tpms.api.domain.OrderSizeDo;
import com.jinpus.tpms.api.domain.WorkOrderDo;
import lombok.Data;

import java.util.List;

/**
 * @className: WorkOrderDto
 * @author: zf
 * @date: 2025/4/7 15:09
 * @version: 1.0
 * @description: 制单业务层对象
 */
@Data
public class WorkOrderDto extends WorkOrderDo {


	private List<OrderColorDto> orderColors;


	private List<OrderFabricDto> orderFabricDto;


	private List<OrderPartDto>  orderPartDto;

	private OrderProcessDo orderProcessDos;

}
