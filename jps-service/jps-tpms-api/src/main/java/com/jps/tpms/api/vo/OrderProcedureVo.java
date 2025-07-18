package com.jps.tpms.api.vo;

import com.jps.tpms.api.domain.OrderProcedureDetailDo;
import com.jps.tpms.api.domain.OrderProcedureDo;
import com.jps.tpms.api.domain.WorkOrderDo;
import lombok.Data;

import java.util.List;

/**
 * @className: OrderProcedureVo
 * @author: zf
 * @date: 2025/5/5 19:44
 * @version: 1.0
 * @description:
 */
@Data
public class OrderProcedureVo extends OrderProcedureDo {

	private WorkOrderDo workOrderDo;

	private List<OrderProcedureDetailDo> orderProcedureDetailDo;
}
