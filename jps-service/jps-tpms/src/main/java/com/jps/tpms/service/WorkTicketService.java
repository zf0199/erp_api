package com.jps.tpms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jps.tpms.api.domain.OrderProcedureDetailDo;
import com.jps.tpms.api.domain.WorkTicketDo;

import java.util.List;

/**
 * @className: WorkTicketService
 * @author: zf
 * @date: 2025/5/19 15:42
 * @version: 1.0
 * @description:
 */
public interface WorkTicketService extends IService<WorkTicketDo> {

	List<OrderProcedureDetailDo> getProcedureDetails(Long workOrderId);


	 void  saveWorkTicket(WorkTicketDo  workTicketDo);
}
