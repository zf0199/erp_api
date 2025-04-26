package com.jinpus.tpms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.OrderPartDo;
import com.jinpus.tpms.api.domain.OrderPartSizeDo;
import com.jinpus.tpms.mapper.OrderPartMapper;
import com.jinpus.tpms.mapper.OrderPartSizeMapper;
import com.jinpus.tpms.service.OrderPartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

/**
 * @className: OrderPartServiceImpl
 * @author: zf
 * @date: 2025/4/23 15:53
 * @version: 1.0
 * @description:
 */
@Service
@AllArgsConstructor
public class OrderPartServiceImpl extends ServiceImpl<OrderPartMapper, OrderPartDo> implements OrderPartService {

	private final OrderPartSizeMapper orderPartSizeMapper;


	/**
	 *  根据部位id删除部位和尺寸
	 * @param id 部位id
	 */
	@Override
	public void deleteById(Long id) {

		boolean b = super.removeById(id);
		LambdaQueryWrapper<OrderPartSizeDo> orderPartSizeDo = Wrappers.lambdaQuery();
		orderPartSizeDo.eq(OrderPartSizeDo::getOrderPartId,id);
		orderPartSizeMapper.delete(orderPartSizeDo);
	}
}
