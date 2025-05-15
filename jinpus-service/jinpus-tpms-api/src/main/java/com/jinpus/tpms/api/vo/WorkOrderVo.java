package com.jinpus.tpms.api.vo;

import com.jinpus.tpms.api.domain.OrderProcessDo;
import com.jinpus.tpms.api.domain.WorkOrderDo;
import com.jinpus.tpms.api.dto.OrderColorDto;
import com.jinpus.tpms.api.dto.OrderPartDto;
import lombok.Data;

import java.util.List;

/**
 * @className: WorkOrderVo
 * @author: zf
 * @date: 2025/4/16 11:14
 * @version: 1.0
 * @description:
 */
@Data
public class WorkOrderVo  extends WorkOrderDo {

	private List<OrderColorDto> orderColors;


	private List<OrderFabricVo> orderFabricVo;

	private List<OrderPartDto> orderPartDtos;

	private OrderProcessDo orderProcessDos;


}
