package com.jinpus.tpms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinpus.tpms.api.domain.OrderProcedureDo;
import com.jinpus.tpms.api.dto.OrderProcedureDto;
import com.jinpus.tpms.api.vo.OrderProcedureVo;

/**
 * @className: OrderProcedureService
 * @author: zf
 * @date: 2025/5/5 17:55
 * @version: 1.0
 * @description:
 */
public interface OrderProcedureService extends IService<OrderProcedureDo> {


	 Page<OrderProcedureVo> getPage(Page<OrderProcedureDo> page,OrderProcedureDo orderProcedureDo);


	OrderProcedureVo getDetail(Long id);


	void update(OrderProcedureDto orderProcedureDto);


}
