package com.jinpus.tpms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.OrderProcedureDetailDo;
import com.jinpus.tpms.api.domain.OrderProcedureDo;
import com.jinpus.tpms.api.domain.WorkOrderDo;
import com.jinpus.tpms.api.vo.OrderProcedureVo;
import com.jinpus.tpms.mapper.OrderProcedureDetailMapper;
import com.jinpus.tpms.mapper.OrderProcedureMapper;
import com.jinpus.tpms.mapper.WorkOrderMapper;
import com.jinpus.tpms.service.OrderProcedureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: OrderProcedureServiceImpl
 * @author: zf
 * @date: 2025/5/5 17:55
 * @version: 1.0
 * @description:
 */
@Service
@AllArgsConstructor
public class OrderProcedureServiceImpl extends ServiceImpl<OrderProcedureMapper,OrderProcedureDo>  implements OrderProcedureService {


	private final WorkOrderMapper workOrderMapper;

	private final OrderProcedureDetailMapper orderProcedureDetailMapper;


	@Override
	public Page<OrderProcedureDo> getPage(Page page) {

		LambdaQueryWrapper<OrderProcedureDo> orderProcedureWrapper = Wrappers.lambdaQuery();
		orderProcedureWrapper.orderByDesc(OrderProcedureDo::getCreateTime);
		Page page1 = this.page(page,orderProcedureWrapper);

		List<OrderProcedureDo> records = page1.getRecords();

		List<OrderProcedureVo> list = records.stream().map(e -> {
			WorkOrderDo workOrderDo = workOrderMapper.selectById(e.getWorkOrderId());
			OrderProcedureVo orderProcedureVo = new OrderProcedureVo();
			BeanUtils.copyProperties(e,orderProcedureVo);
			orderProcedureVo.setWorkOrderDo(workOrderDo);
			return orderProcedureVo;
		}).toList();
		page1.setRecords(list);

		return page1;
	}

	@Override
	public OrderProcedureVo getDetail(Long id) {
		OrderProcedureDo orderProcedureDo = this.getById(id);
		Long workOrderId = orderProcedureDo.getWorkOrderId();
		WorkOrderDo workOrderDo = workOrderMapper.selectById(workOrderId);

		OrderProcedureVo orderProcedureVo = new OrderProcedureVo();
		BeanUtils.copyProperties(orderProcedureDo,orderProcedureVo);
		orderProcedureVo.setWorkOrderDo(workOrderDo);

		LambdaQueryWrapper<OrderProcedureDetailDo> orderProcedureDetailWrapper = Wrappers.lambdaQuery();
		orderProcedureDetailWrapper.eq(OrderProcedureDetailDo::getOrderProcedureId,id);
		List<OrderProcedureDetailDo> orderProcedureDetailDos = orderProcedureDetailMapper.selectList(orderProcedureDetailWrapper);
		orderProcedureVo.setOrderProcedureDetailDo(orderProcedureDetailDos);
		return orderProcedureVo;
	}


}
