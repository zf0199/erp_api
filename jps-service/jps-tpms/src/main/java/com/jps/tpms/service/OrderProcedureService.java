package com.jps.tpms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jps.tpms.api.domain.OrderProcedureDetailDo;
import com.jps.tpms.api.domain.OrderProcedureDo;
import com.jps.tpms.api.dto.OrderProcedureDto;
import com.jps.tpms.api.vo.OrderProcedureVo;

import java.util.List;

/**
 * @className: OrderProcedureService
 * @author: zf
 * @date: 2025/5/5 17:55
 * @version: 1.0
 * @description:
 */
public interface OrderProcedureService extends IService<OrderProcedureDo> {


	 Page<OrderProcedureVo> getPage(Page<OrderProcedureDo> page, OrderProcedureDo orderProcedureDo);


	OrderProcedureVo getDetail(Long id);


	void update(OrderProcedureDto orderProcedureDto);


	List<OrderProcedureDetailDo> getProcedureProgress(Long id);

}
