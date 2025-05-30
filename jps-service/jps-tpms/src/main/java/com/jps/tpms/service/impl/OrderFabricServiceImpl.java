package com.jps.tpms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.tpms.api.domain.OrderColorDo;
import com.jps.tpms.api.domain.OrderFabricColorSizeDo;
import com.jps.tpms.api.domain.OrderFabricDo;
import com.jps.tpms.api.domain.OrderSizeDo;
import com.jps.tpms.api.dto.FabricColorDto;
import com.jps.tpms.api.dto.FabricSizeDto;
import com.jps.tpms.api.dto.OrderFabricDto;
import com.jps.tpms.api.vo.FabricColorSizeVo;
import com.jps.tpms.mapper.OrderColorMapper;
import com.jps.tpms.mapper.OrderFabricColorSizMapper;
import com.jps.tpms.mapper.OrderFabricMapper;
import com.jps.tpms.mapper.OrderSizeMapper;
import com.jps.tpms.service.OrderFabricService;
import lombok.AllArgsConstructor;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @className: OrderFabricServiceImpl
 * @author: zf
 * @date: 2025/4/22 11:05
 * @version: 1.0
 * @description:
 */
@Service
@AllArgsConstructor

public class OrderFabricServiceImpl extends ServiceImpl<OrderFabricMapper, OrderFabricDo> implements OrderFabricService {

	private final OrderFabricColorSizMapper orderFabricColorSizMapper;

	private final OrderColorMapper orderColorMapper;

	private final OrderSizeMapper orderSizeMapper;

	@Override
	public void deleteById(Long id) {
		boolean b = super.removeById(id);
		LambdaQueryWrapper<OrderFabricColorSizeDo> orderFabricColorSizeDo = Wrappers.lambdaQuery();
		orderFabricColorSizeDo.eq(OrderFabricColorSizeDo::getOrderFabricId, id);
		orderFabricColorSizMapper.delete(orderFabricColorSizeDo);
	}

	public void updateFabricOne(OrderFabricDto orderFabric) {
		Long orderFabricId = orderFabric.getId();
		Long workOrderId = orderFabric.getWorkOrderId();
		OrderFabricDo orderFabricDo = new OrderFabricDo();
		BeanUtils.copyProperties(orderFabric, orderFabricDo);
		this.updateById(orderFabricDo);

		Long id = orderFabricDo.getId();
		LambdaQueryWrapper<OrderFabricColorSizeDo> orderFabricColorSizeDoLambdaQueryWrapper = Wrappers.lambdaQuery();
		orderFabricColorSizeDoLambdaQueryWrapper.eq(OrderFabricColorSizeDo::getOrderFabricId, id);
		orderFabricColorSizMapper.delete(orderFabricColorSizeDoLambdaQueryWrapper);


		List<FabricColorDto> fabricColorDtos = orderFabric.getFabricColorDtos();
		List<FabricSizeDto> fabricSizeDtos = orderFabric.getFabricSizeDtos();

		// 不配色配码
		if (orderFabric.getIsMatchingSize() == 1 && orderFabric.getIsColorScheme() == 1) {
			return;
		}

		if (orderFabric.getIsMatchingSize() == 0 && orderFabric.getIsColorScheme() == 1) {
			List<OrderFabricColorSizeDo> list = fabricSizeDtos.stream().map(e -> {

				OrderFabricColorSizeDo orderFabricColorSizeDo = new OrderFabricColorSizeDo();
				BeanUtils.copyProperties(e, orderFabricColorSizeDo);
				orderFabricColorSizeDo.setOrderFabricId(orderFabricId);
				orderFabricColorSizeDo.setWorkOrderId(workOrderId);
				return orderFabricColorSizeDo;
			}).toList();
			orderFabricColorSizMapper.insert(list);
			return;
		}

		if (orderFabric.getIsMatchingSize() == 1 && orderFabric.getIsColorScheme() == 0) {
			List<OrderFabricColorSizeDo> list = fabricColorDtos.stream().map(e -> {
				OrderFabricColorSizeDo orderFabricColorSizeDo = new OrderFabricColorSizeDo();
				BeanUtils.copyProperties(e, orderFabricColorSizeDo);
				orderFabricColorSizeDo.setOrderFabricId(orderFabricId);
				orderFabricColorSizeDo.setWorkOrderId(workOrderId);
				return orderFabricColorSizeDo;
			}).toList();
			orderFabricColorSizMapper.insert(list);
			return;
		}

		if (orderFabric.getIsMatchingSize() == 0 && orderFabric.getIsColorScheme() == 0) {
			List<OrderFabricColorSizeDo> result = fabricColorDtos.stream()
					.flatMap(e -> fabricSizeDtos.stream().map(t -> {
						OrderFabricColorSizeDo orderFabricColorSizeDo = new OrderFabricColorSizeDo();
						BeanUtils.copyProperties(e, orderFabricColorSizeDo);
						BeanUtils.copyProperties(t, orderFabricColorSizeDo);
						orderFabricColorSizeDo.setOrderFabricId(orderFabricId);
						orderFabricColorSizeDo.setWorkOrderId(workOrderId);
						return orderFabricColorSizeDo;
					}))
					.toList();
			orderFabricColorSizMapper.insert(result);
		}
	}

	@Override
	public FabricColorSizeVo selectFabricColorAndSize(String tag, Long workOrderId, Long orderFabricId) {

		// 查询改物料是否有配色配码
		LambdaQueryWrapper<OrderFabricColorSizeDo> orderFabricColorSizeWrapper = Wrappers.lambdaQuery();
		orderFabricColorSizeWrapper.eq(OrderFabricColorSizeDo::getOrderFabricId, orderFabricId);
		List<OrderFabricColorSizeDo> orderFabricColorSizeDos = orderFabricColorSizMapper.selectList(orderFabricColorSizeWrapper);

		List<FabricSizeDto> fabricSizeDtos = getFabricSizeDtos(workOrderId);
		List<FabricColorDto> fabricColorDtos = getFabricColorDtos(workOrderId);


//		Long  workOrderId = 1914577808022888449l;
		FabricColorSizeVo fabricColorSizeVo = new FabricColorSizeVo();

		if (orderFabricColorSizeDos.isEmpty()) {
			fabricColorSizeVo.setFabricSizeDtos(fabricSizeDtos);
			fabricColorSizeVo.setFabricColorDtos(fabricColorDtos);
			return fabricColorSizeVo;
		}

		List<FabricColorDto> fabricColorList = orderFabricColorSizeDos.stream().filter(it->it.getOrderColor()!=null).map(order -> {

			FabricColorDto colorDto = new FabricColorDto();
			BeanUtils.copyProperties(order, colorDto);
			return colorDto;
		}).collect(Collectors.collectingAndThen(
				Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(FabricColorDto::getOrderColorNo))),
				ArrayList::new
		));
		// 拆出尺码集合（去重）
		List<FabricSizeDto> fabricSizeList = orderFabricColorSizeDos.stream().filter(it->it.getOrderSize()!=null)
				.map(order -> {
					FabricSizeDto sizeDto = new FabricSizeDto();
					BeanUtils.copyProperties(order, sizeDto);
					return sizeDto;
				}).collect(Collectors.collectingAndThen(
						Collectors.toCollection(() -> new TreeSet<>(
								Comparator.comparing(
										FabricSizeDto::getOrderSize,
										Comparator.nullsFirst(String::compareTo) // 或使用 nullsLast
								)
						)),
						ArrayList::new
				));

		if (fabricColorList.isEmpty()){
			fabricColorList = fabricColorDtos;
		}else if (fabricSizeList.isEmpty()){
			fabricSizeList = fabricSizeDtos;
		}else {
			fabricColorList = filterByIntersection(fabricColorList, fabricColorDtos, FabricColorDto::getOrderColor);

			fabricSizeList = filterByIntersection(fabricSizeList, fabricSizeDtos, FabricSizeDto::getOrderSize);
		}
		// TAG selectFabricColorAndSize :   bug 颜色尺码尺删掉颜色 物料配色没有删除

		fabricColorSizeVo.setOrderFabricId(orderFabricId);
		fabricColorSizeVo.setFabricColorDtos(fabricColorList);
		fabricColorSizeVo.setFabricSizeDtos(fabricSizeList);
		return fabricColorSizeVo;
	}


	public List<FabricColorDto> getFabricColorDtos(Long workOrderId) {
		// 查询制单颜色
		LambdaQueryWrapper<OrderColorDo> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(OrderColorDo::getWorkOrderId, workOrderId).orderByDesc(OrderColorDo::getCreateTime);
		List<OrderColorDo> orderColorDos = orderColorMapper.selectList(wrapper);
		return orderColorDos.stream().map(s -> {
			FabricColorDto fabricColorDto = new FabricColorDto();
			fabricColorDto.setOrderColor(s.getName());
			fabricColorDto.setOrderColorNo(s.getNo());
			return fabricColorDto;
		}).toList();
	}


	public List<FabricSizeDto> getFabricSizeDtos(Long workOrderId) {
		// 查询制单尺码
		List<OrderSizeDo> orderSizeDo = orderSizeMapper.selectOrderSizeDistinctSize(workOrderId);
		return orderSizeDo.stream().map(e -> {
			FabricSizeDto fabricSizeDto = new FabricSizeDto();
			fabricSizeDto.setOrderSize(e.getSizeName());
			fabricSizeDto.setOrderSizeNum(e.getSizeNum());
			fabricSizeDto.setOrderSort(e.getSort());
			return fabricSizeDto;
		}).toList();
	}
	/**
	 * 根据 key 提取函数，从 sourceList 中筛选出 key 值与 referenceList 匹配的元素
	 *
	 * @param sourceList     需要被过滤的列表
	 * @param referenceList  作为参照的列表（只保留与其 key 匹配的元素）
	 * @param keyExtractor   获取 key 的函数（如 FabricColorDto::getOrderColor）
	 * @return               被过滤后的列表
	 */
	public  <T, R> List<T> filterByIntersection(List<T> sourceList, List<T> referenceList, Function<T, R> keyExtractor) {

		if (sourceList == null || referenceList == null || keyExtractor == null) {
			return Collections.emptyList();
		}

		// 提取 referenceList 中所有的 key
		Set<R> referenceKeys = referenceList.stream()
				.map(keyExtractor)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

		// 将 sourceList 按 key 映射为 Map，方便查找
		Map<R, T> sourceMap = sourceList.stream()
				.filter(item -> keyExtractor.apply(item) != null)
				.collect(Collectors.toMap(keyExtractor, Function.identity(), (a, b) -> a));

		// 最终构造的结果：遍历 referenceList，按 key 从 sourceMap 找，没有就用 referenceList 自身的对象
		return referenceList.stream()
				.map(item -> {
					R key = keyExtractor.apply(item);
					return sourceMap.getOrDefault(key, item);
				})
				.distinct()
				.collect(Collectors.toList());


	}

}
