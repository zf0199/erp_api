package com.jps.tpms.api.vo;

import com.jps.tpms.api.domain.OrderColorDo;
import com.jps.tpms.api.domain.OrderProcedureDetailDo;
import com.jps.tpms.api.domain.OrderSizeDo;
import lombok.Data;

import java.util.List;

/**
 * @className: WorkOrderAppVo
 * @author: zf
 * @date: 2025/5/21 10:38
 * @version: 1.0
 * @description:
 */
@Data
public class WorkOrderInfoAppVo {

	private Long id;

	private String styleNo;

	private List<OrderColorDo> orderColorDos;

	private List<OrderSizeDo> orderSizeDos;

	private List<OrderProcedureDetailDo> orderProcedureDetailDo;
}
