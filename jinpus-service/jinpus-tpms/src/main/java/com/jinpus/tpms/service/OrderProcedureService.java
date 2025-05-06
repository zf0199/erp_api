package com.jinpus.tpms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinpus.tpms.api.domain.OrderProcedureDo;
import com.jinpus.tpms.api.vo.OrderProcedureVo;

/**
 * @className: OrderProcedureService
 * @author: zf
 * @date: 2025/5/5 17:55
 * @version: 1.0
 * @description:
 */
public interface OrderProcedureService extends IService<OrderProcedureDo> {


	 Page<OrderProcedureDo> getPage(Page page);


	OrderProcedureVo getDetail(Long id);


}
