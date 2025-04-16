package com.jinpus.tpms.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.OrderColorDo;
import com.jinpus.tpms.api.domain.OrderSizeDo;
import com.jinpus.tpms.api.domain.WorkOrderDo;
import com.jinpus.tpms.api.dto.OrderColorDto;
import com.jinpus.tpms.api.dto.WorkOrderDto;
import com.jinpus.tpms.mapper.OrderColorMapper;
import com.jinpus.tpms.mapper.OrderSizeMapper;
import com.jinpus.tpms.mapper.WorkOrderMapper;
import com.jinpus.tpms.service.WorkOrderService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @className: WorkOrderService
 * @author: zf
 * @date: 2025-03-20 20:02:51
 * @version: 1.0
 * @description: 生产制单基础表 业务层
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrderDo> implements WorkOrderService {

	private final OrderSizeMapper orderSizeMapper;
	private final OrderColorMapper orderColorMapper;

	@Override
	public Long add(WorkOrderDto workOrderDto) {

		WorkOrderDo workOrderDo = new WorkOrderDo();
		BeanUtils.copyProperties(workOrderDto, workOrderDo);
		this.save(workOrderDo);

		Long workOrderId = workOrderDo.getId();

		Optional.ofNullable(workOrderDto.getOrderColors()).filter(l -> !l.isEmpty()).ifPresent(e -> e.forEach(t -> {
			List<OrderColorDto> orderColors = workOrderDto.getOrderColors();
			orderColors.forEach(s -> {
				OrderColorDo orderColorDo = new OrderColorDo();
				BeanUtils.copyProperties(s, orderColorDo);
				orderColorMapper.insert(orderColorDo);
				Long id = orderColorDo.getId();
				List<OrderSizeDo> orderSizes = t.getOrderSizes();
				orderSizes.forEach(m -> {
					m.setOrderColorId(id);
					orderSizeMapper.insert(m);
				});
			});
		}));
		return workOrderId;
	}

	@Override
	public List<WorkOrderDto> getList(WorkOrderDo workOrderDo) {

		List<WorkOrderDo> list = this.list(Wrappers.query(workOrderDo));
		Long id = workOrderDo.getId();

		LambdaQueryWrapper<OrderColorDo> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(OrderColorDo::getWorkOrderId, id);

		List<OrderColorDo> orderColorDos = orderColorMapper.selectList(wrapper);

		List<OrderColorDto> collect = orderColorDos.stream().map(e -> {
			LambdaQueryWrapper<OrderSizeDo> sizeDoLambdaQueryWrapper = Wrappers.lambdaQuery();
			sizeDoLambdaQueryWrapper.eq(OrderSizeDo::getOrderColorId, e.getId());
			OrderColorDto orderColorDto = new OrderColorDto();

			BeanUtils.copyProperties(e, orderColorDto);
			List<OrderSizeDo> orderSizeDos = orderSizeMapper.selectList(sizeDoLambdaQueryWrapper);
			orderColorDto.setOrderSizes(orderSizeDos);
			return orderColorDto;
		}).collect(Collectors.toList());

		return list.stream().map(t -> {
			WorkOrderDto workOrderDto = new WorkOrderDto();
			BeanUtils.copyProperties(t, workOrderDto);
			workOrderDto.setOrderColors(collect);
			return workOrderDto;
		}).collect(Collectors.toList());
	}

	@Override
	public void updateById(WorkOrderDto workOrderDto) {
		Long workOrderId = workOrderDto.getId();
		// 更新制单基础数据
		WorkOrderDo workOrderDo = new WorkOrderDo();
		BeanUtils.copyProperties(workOrderDto, workOrderDo);
		this.updateById(workOrderDo);

		// 查询
		LambdaQueryWrapper<OrderColorDo> objectLambdaQueryWrapper = Wrappers.lambdaQuery();
		objectLambdaQueryWrapper.eq(OrderColorDo::getWorkOrderId, workOrderDto.getId());
		List<OrderColorDo> orderColorDos1 = orderColorMapper.selectList(objectLambdaQueryWrapper);

		if (CollectionUtils.isNotEmpty(orderColorDos1)){
			// 根据原id删除颜色
			List<Long> orderIds = orderColorDos1.stream().map(OrderColorDo::getId).toList();
			orderColorMapper.deleteByIds(orderIds);
			// 删除尺寸
			LambdaQueryWrapper<OrderSizeDo> orderSizeWrapper = Wrappers.lambdaQuery();
			orderSizeWrapper.in(OrderSizeDo::getOrderColorId, orderIds);
			orderSizeMapper.delete(orderSizeWrapper);

		}





//		// 获取表单提交的数据是否有id
//		Map<Boolean, List<OrderColorDo>> collect = orderColors.stream().map(e -> {
//			OrderColorDo orderColorDo = new OrderColorDo();
//			BeanUtils.copyProperties(e, orderColorDo);
//			return orderColorDo;
//		}).collect(Collectors.partitioningBy(t -> ObjectUtils.isNotEmpty(t.getId())));

		List<OrderColorDto> orderColors = workOrderDto.getOrderColors();

//		if (!orderColors.isEmpty()){
//			orderColors.forEach(t->{
//				if (ObjectUtils.isNotEmpty(t.getId())) {
//					OrderColorDo orderColorDo = new OrderColorDo();
//					BeanUtils.copyProperties(t, orderColorDo);
//					orderColorDo.setWorkOrderId(workOrderId);
//					orderColorMapper.insert(orderColorDo);
//					Long id = orderColorDo.getId();
//					List<OrderSizeDo> orderSizes = t.getOrderSizes();
//					orderSizes.forEach(s -> s.setOrderColorId(id));
//					orderSizeMapper.insert(orderSizes);
//				}
//
//			});
//		}

		Optional.ofNullable(orderColors).filter(l -> !l.isEmpty()).ifPresent(e -> e.forEach(t -> {
				OrderColorDo orderColorDo = new OrderColorDo();
				BeanUtils.copyProperties(t, orderColorDo);
				orderColorDo.setWorkOrderId(workOrderId);
				orderColorMapper.insertOrUpdate(orderColorDo);
				Long id = orderColorDo.getId();
				List<OrderSizeDo> orderSizes = t.getOrderSizes();
				orderSizes.forEach(s -> s.setOrderColorId(id));
				orderSizeMapper.insert(orderSizes);
		}));
	}
}
