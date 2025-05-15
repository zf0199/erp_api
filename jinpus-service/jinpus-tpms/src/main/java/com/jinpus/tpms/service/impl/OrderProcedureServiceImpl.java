package com.jinpus.tpms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.OrderProcedureDetailDo;
import com.jinpus.tpms.api.domain.OrderProcedureDo;
import com.jinpus.tpms.api.domain.WorkOrderDo;
import com.jinpus.tpms.api.dto.OrderProcedureDto;
import com.jinpus.tpms.api.vo.OrderProcedureVo;
import com.jinpus.tpms.mapper.OrderProcedureDetailMapper;
import com.jinpus.tpms.mapper.OrderProcedureMapper;
import com.jinpus.tpms.mapper.WorkOrderMapper;
import com.jinpus.tpms.service.OrderProcedureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

	private final OrderProcedureMapper orderProcedureMapper;


	@Override
	public Page<OrderProcedureVo> getPage(Page<OrderProcedureDo> page,OrderProcedureDo orderProcedureDo) {

		LambdaQueryWrapper<OrderProcedureDo> orderProcedureWrapper = Wrappers.lambdaQuery();
		orderProcedureWrapper.orderByDesc(OrderProcedureDo::getCreateTime);

		Page<OrderProcedureDo> orderProcedureDoPage = this.page(page,orderProcedureWrapper);
		List<OrderProcedureDo> records = orderProcedureDoPage.getRecords();
		Page<OrderProcedureVo> orderProcedureVoPage = new Page<>();
		BeanUtils.copyProperties(orderProcedureDoPage,orderProcedureVoPage);

		List<OrderProcedureVo> list = records.stream().map(e -> {
			WorkOrderDo workOrderDo = workOrderMapper.selectById(e.getWorkOrderId());
			OrderProcedureVo orderProcedureVo = new OrderProcedureVo();
			BeanUtils.copyProperties(e,orderProcedureVo);
			orderProcedureVo.setWorkOrderDo(workOrderDo);
			return orderProcedureVo;
		}).toList();
		orderProcedureVoPage.setRecords(list);
		return orderProcedureVoPage;
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

	@Override
	public void update(OrderProcedureDto orderProcedureDto) {


		Long orderProcedureId = orderProcedureDto.getId();

		// 删除所有的工序工价明细
		LambdaQueryWrapper<OrderProcedureDetailDo> orderProcedureDetailDoWrapper = Wrappers.lambdaQuery();
		orderProcedureDetailDoWrapper.eq(OrderProcedureDetailDo::getOrderProcedureId,orderProcedureId);
		orderProcedureDetailMapper.delete(orderProcedureDetailDoWrapper);

		List<OrderProcedureDetailDo> orderProcedureDetailDos = orderProcedureDto.getOrderProcedureDetailDos();

		int size = orderProcedureDetailDos.size();
		BigDecimal reduce = Optional.ofNullable(orderProcedureDetailDos)
				.orElse(Collections.emptyList())
				.stream()
				.map(OrderProcedureDetailDo::getPrice)
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		LambdaUpdateWrapper<OrderProcedureDo> orderProcedureDoUpdateWrapper = Wrappers.lambdaUpdate();

		orderProcedureDoUpdateWrapper.eq(OrderProcedureDo::getCutOrderId,orderProcedureId)
				.set(OrderProcedureDo::getProcedureNum,size)
						.set(OrderProcedureDo::getProcedurePrice,reduce);
		orderProcedureMapper.update(orderProcedureDoUpdateWrapper);


		// 新增
		orderProcedureDetailDos.forEach(e->{
			e.setOrderProcedureId(orderProcedureId);
			orderProcedureDetailMapper.insert(e);
		});
	}


}
