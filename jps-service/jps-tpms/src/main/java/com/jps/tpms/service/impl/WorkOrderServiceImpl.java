package com.jps.tpms.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.tpms.api.vo.FabricColorSizeVo;
import com.jps.tpms.api.vo.OrderFabricVo;
import com.jps.tpms.api.vo.WorkOrderInfoAppVo;
import com.jps.tpms.api.vo.WorkOrderVo;
import com.jps.tpms.mapper.*;
import com.jps.tpms.service.WorkOrderService;
import com.jps.tpms.api.domain.*;
import com.jps.tpms.api.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
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

	private final OrderFabricMapper orderFabricMapper;

	private final OrderFabricSizeMapper orderFabricSizeMapper;

	private final OrderFabricColorMapper orderFabricColorMapper;


	private final OrderFabricColorSizMapper orderFabricColorSizMapper;

	private final OrderPartMapper orderPartMapper;

	private final OrderPartSizeMapper orderPartSizeMapper;

	private final OrderProcessMapper orderProcessMapper;

	private final CutOrderMapper cutOrderMapper;

	private final OrderProcedureMapper orderProcedureMapper;

	private final StyleMaterialMapper styleMaterialMapper;

	private final StylePartMapper stylePartMapper;

	private final StyleProcedureMapper  styleProcedureMapper;

	private final OrderProcedureDetailMapper orderProcedureDetailMapper;

	@Override
	public Long add(WorkOrderDto workOrderDto) {

		WorkOrderDo workOrderDo = new WorkOrderDo();
		BeanUtils.copyProperties(workOrderDto, workOrderDo);
		this.save(workOrderDo);
		Long workOrderId = workOrderDo.getId();
		Long styleId = workOrderDto.getStyleId();

		// 新增裁床单
		CutOrderDo cutOrderDo = new CutOrderDo();
		cutOrderDo.setWorkOrderId(workOrderId);
		String upperCase = workOrderDo.getOrderNo().toUpperCase();
		if (ObjectUtils.isEmpty(upperCase)){
			throw new NullPointerException("生成裁床单时  制单单号为null ");
		}
		cutOrderDo.setCutOrderNo("C" + upperCase );
		cutOrderDo.setProgress(0);
		cutOrderMapper.insert(cutOrderDo);
		Long cutOrderId = cutOrderDo.getId();

		LambdaQueryWrapper<StyleProcedureDo> styleProcedureDoWrapper = Wrappers.lambdaQuery();
		styleProcedureDoWrapper.eq(StyleProcedureDo::getStyleId, styleId);
		List<StyleProcedureDo> styleProcedureDos = styleProcedureMapper.selectList(styleProcedureDoWrapper);
		// 新增工序工价
		OrderProcedureDo orderProcedureDo = new OrderProcedureDo();
		orderProcedureDo.setWorkOrderId(workOrderId);
		orderProcedureDo.setCutOrderId(cutOrderId);
		orderProcedureDo.setProcedurePrice(BigDecimal.ZERO);
		orderProcedureDo.setCutNum(0);
		orderProcedureDo.setBedNum(0);
		orderProcedureDo.setProcedureNum(styleProcedureDos.size());
		orderProcedureMapper.insert(orderProcedureDo);
		Long orderProcedureId = orderProcedureDo.getId();

		// 带出款类的默认工序
		styleProcedureDos.stream().forEach(e->{
			OrderProcedureDetailDo orderProcedureDetailDo = new OrderProcedureDetailDo();
			orderProcedureDetailDo.setOrderProcedureId(orderProcedureId);
			// 工序代号
			orderProcedureDetailDo.setNo(e.getNo());
			// 工序名称
			orderProcedureDetailDo.setName(e.getName());
			// 工价
			orderProcedureDetailDo.setPrice(e.getPrice());
			// 津贴比例
			orderProcedureDetailDo.setSubsidyRate(e.getSubsidyRate());
			// 补贴金额
			orderProcedureDetailDo.setSubsidyAmount(e.getSubsidyAmount());
			orderProcedureDetailDo.setRemark(e.getRemark());
			orderProcedureDetailDo.setProcedureTypeId(e.getProcedureTypeId());
			orderProcedureDetailMapper.insert(orderProcedureDetailDo);

		});

//		Optional.ofNullable(workOrderDto.getOrderColors()).filter(l -> !l.isEmpty()).ifPresent(e -> e.forEach(t -> {
//			List<OrderColorDto> orderColors = workOrderDto.getOrderColors();
//			orderColors.forEach(s -> {
//				OrderColorDo orderColorDo = new OrderColorDo();
//				BeanUtils.copyProperties(s, orderColorDo);
//				orderColorMapper.insert(orderColorDo);
//				Long id = orderColorDo.getId();
//				List<OrderSizeDo> orderSizes = t.getOrderSizes();
//				orderSizes.forEach(m -> {
//					m.setOrderColorId(id);
//					orderSizeMapper.insert(m);
//				});
//			});
//		}));

		// 同步款类物料到制单
		LambdaQueryWrapper<StyleMaterialDo> styleMaterialDoWrapper = Wrappers.lambdaQuery();
		LambdaQueryWrapper<StyleMaterialDo> eq = styleMaterialDoWrapper.eq(StyleMaterialDo::getStyleId, styleId);
		List<StyleMaterialDo> styleMaterialDos = styleMaterialMapper.selectList(eq);
		 styleMaterialDos.stream().forEach(e -> {
			OrderFabricDo orderFabricDo = new OrderFabricDo();
			// 制单id
			orderFabricDo.setWorkOrderId(workOrderId);
			// 物料名称
			orderFabricDo.setName(e.getName());
			// 物料编号
			orderFabricDo.setNo(e.getNo());

			// 物料大类
			orderFabricDo.setMaterialTypeId(e.getMaterialTypeId());
			// 规格成分
			orderFabricDo.setComposition(e.getComposition());
			// 布封
			orderFabricDo.setWidht(e.getWidht());
			// 克重
			orderFabricDo.setWeight(e.getWeight());
			// 单价
			orderFabricDo.setPrice(e.getPrice());
			orderFabricDo.setRemark(e.getRemark());
			 orderFabricDo.setMaterialCategory(e.getMaterialCategory());
			 orderFabricDo.setIsColorScheme(0);
			 orderFabricDo.setIsMatchingSize(0);
			orderFabricMapper.insert(orderFabricDo);
		});

		 // 同步款类制单尺寸到制单
		LambdaQueryWrapper<StylePartDo> stylePartDoWrapper = Wrappers.lambdaQuery();
		stylePartDoWrapper.eq(StylePartDo::getStyleId, styleId);
		List<StylePartDo> stylePartDos = stylePartMapper.selectList(stylePartDoWrapper);
		stylePartDos.forEach(e->{
			OrderPartDo orderPartDo = new OrderPartDo();
			orderPartDo.setWorkOrderId(workOrderId);
			orderPartDo.setNo(e.getNo());
			orderPartDo.setPartName(e.getPartName());
			orderPartDo.setDimMethod(e.getDimMethod());
			orderPartDo.setBasicSize(e.getBasicSize());

			orderPartMapper.insert(orderPartDo);
		});




		return workOrderId;
	}

	@Override
	public WorkOrderVo getList(String tag, WorkOrderDo workOrderDo) {
		// 查询制单基础数据
		return switch (tag) {
			case "0" -> getColorAndSize(this.getById(workOrderDo.getId()));
			case "1" -> getFabric(tag, this.getById(workOrderDo.getId()));
			case "2" -> getFabric(tag, this.getById(workOrderDo.getId()));
			case "3" -> getFabric(tag, this.getById(workOrderDo.getId()));
			case "4" -> getFabric(tag, this.getById(workOrderDo.getId()));
			case "5" -> getOrderPart(tag, this.getById(workOrderDo.getId()));
			case "6" -> getOrderProcess(this.getById(workOrderDo.getId()));

			default -> throw new IllegalArgumentException("不支持的tag类型: " + tag);
		};
	}

	@Override
	public void updateById(String tag, WorkOrderDto workOrderDto) {
		Long workOrderId = workOrderDto.getId();
		// 更新制单基础数据
		WorkOrderDo workOrderDo = new WorkOrderDo();
		BeanUtils.copyProperties(workOrderDto, workOrderDo);
		this.updateById(workOrderDo);


		switch (tag) {
			case "0" -> updateColorAndSize(workOrderId, workOrderDto.getOrderColors());

			case "1" -> updateFabric(tag, workOrderId, workOrderDto.getOrderFabricDto());
			case "2" -> updateFabric(tag, workOrderId, workOrderDto.getOrderFabricDto());
			case "3" -> updateFabric(tag, workOrderId, workOrderDto.getOrderFabricDto());
			case "4" -> updateFabric(tag, workOrderId, workOrderDto.getOrderFabricDto());
			case "5" -> updateOrderPart(workOrderId, workOrderDto.getOrderPartDto());
			case "6" -> updateOrderProcess(workOrderId, workOrderDto.getOrderProcessDo());
			default -> throw new IllegalArgumentException("不支持的tag类型: " + tag);

		}

	}


	@Override
	public FabricColorSizeVo getColorAndSize(Long id) {

		// 查询颜色数据
		LambdaQueryWrapper<OrderColorDo> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(OrderColorDo::getWorkOrderId, id).orderByDesc(OrderColorDo::getCreateTime);

		List<OrderColorDo> orderColorDos = orderColorMapper.selectList(wrapper);

		LambdaQueryWrapper<OrderSizeDo> orderSizeDoWrapper = Wrappers.lambdaQuery();
		orderSizeDoWrapper.eq(OrderSizeDo::getWorkOrderId, id).orderByDesc(OrderSizeDo::getCreateTime);
		List<OrderSizeDo> orderSizes = orderSizeMapper.selectList(orderSizeDoWrapper);
		List<OrderSizeDo> orderSizeDos = orderSizeMapper.selectOrderSizeDistinctSize(id);
		Collections.reverse(orderSizeDos);

		// 按照sizeName去重对象
		List<OrderSizeDo> distinctList = orderSizes.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.toCollection(() ->
								new TreeSet<>(Comparator.comparing(OrderSizeDo::getSizeName))
						),
						ArrayList::new
				));
		FabricColorSizeVo fabricColorSizeVo = new FabricColorSizeVo();


		List<FabricColorDto> list = orderColorDos.stream().map(s -> {
			FabricColorDto fabricColorDto = new FabricColorDto();
			fabricColorDto.setOrderColor(s.getName());
			fabricColorDto.setOrderColorNo(s.getNo());
			return fabricColorDto;
		}).toList();

		List<FabricSizeDto> list1 = orderSizeDos.stream().map(size -> {
			FabricSizeDto fabricSizeDto = new FabricSizeDto();
			fabricSizeDto.setOrderSize(size.getSizeName());
			fabricSizeDto.setOrderSizeNum(size.getSizeNum());
			fabricSizeDto.setOrderSort(size.getSort());
			return fabricSizeDto;
		}).toList();

		fabricColorSizeVo.setFabricSizeDtos(list1);
		fabricColorSizeVo.setFabricColorDtos(list);


//		colorSizeVo.setOrderColors(orderColorDos);
//		colorSizeVo.setOrderSizes(distinctList);
//		colorSizeVo.setWorkOrderId(id);

		return fabricColorSizeVo;
	}

	@Override
	public WorkOrderInfoAppVo getWorkOrderInfo(Long id) {

		WorkOrderInfoAppVo workOrderInfoAppVo = new WorkOrderInfoAppVo();

		WorkOrderDo byId = this.getById(id);
		String styleNo = byId.getStyleNo();

		LambdaQueryWrapper<OrderProcedureDetailDo> orderProcedureDetailWrapper = Wrappers.lambdaQuery();
		orderProcedureDetailWrapper.eq(OrderProcedureDetailDo::getWorkOrderId,id);
		List<OrderProcedureDetailDo> orderProcedureDetailDos = orderProcedureDetailMapper.selectList(orderProcedureDetailWrapper);


		workOrderInfoAppVo.setId(id);
		workOrderInfoAppVo.setStyleNo(byId.getStyleNo());
		workOrderInfoAppVo.setOrderProcedureDetailDo(orderProcedureDetailDos);

		LambdaQueryWrapper<OrderColorDo> orderColorWrapper = Wrappers.lambdaQuery();
		orderColorWrapper.eq(OrderColorDo::getWorkOrderId,id);
		List<OrderColorDo> orderColorDos = orderColorMapper.selectList(orderColorWrapper);
		workOrderInfoAppVo.setOrderColorDos(orderColorDos);

		return workOrderInfoAppVo;

	}


	public void updateColorAndSize(Long workOrderId, List<OrderColorDto> orderColorDtos) {

		// 查询
		LambdaQueryWrapper<OrderColorDo> objectLambdaQueryWrapper = Wrappers.lambdaQuery();
		objectLambdaQueryWrapper.eq(OrderColorDo::getWorkOrderId, workOrderId);
		List<OrderColorDo> orderColorDos1 = orderColorMapper.selectList(objectLambdaQueryWrapper);

		if (CollectionUtils.isNotEmpty(orderColorDos1)) {
			// 根据原id删除颜色
			List<Long> orderIds = orderColorDos1.stream().map(OrderColorDo::getId).toList();
			orderColorMapper.deleteByIds(orderIds, false);
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

//		List<OrderColorDto> orderColors = workOrderDto.getOrderColors();
		Optional.ofNullable(orderColorDtos).filter(l -> !l.isEmpty()).ifPresent(e -> e.forEach(t -> {
			OrderColorDo orderColorDo = new OrderColorDo();
			BeanUtils.copyProperties(t, orderColorDo);
			orderColorDo.setWorkOrderId(workOrderId);
			orderColorMapper.insertOrUpdate(orderColorDo);
			Long id = orderColorDo.getId();
			List<OrderSizeDo> orderSizes = t.getOrderSizes();
			orderSizes.forEach(s -> {
				s.setOrderColorId(id);
				s.setWorkOrderId(workOrderId);
			});
			orderSizeMapper.insert(orderSizes);
		}));
	}

	public WorkOrderVo getColorAndSize(WorkOrderDo workOrder) {

		// 查询颜色数据
		LambdaQueryWrapper<OrderColorDo> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(OrderColorDo::getWorkOrderId, workOrder.getId()).orderByDesc(OrderColorDo::getCreateTime);
		List<OrderColorDo> orderColorDos = orderColorMapper.selectList(wrapper);

		// 查询 尺码 并封装成 OrderColorDto
		List<OrderColorDto> collect = orderColorDos.stream().map(e -> {
			LambdaQueryWrapper<OrderSizeDo> sizeDoLambdaQueryWrapper = Wrappers.lambdaQuery();
			sizeDoLambdaQueryWrapper.eq(OrderSizeDo::getOrderColorId, e.getId());
			OrderColorDto orderColorDto = new OrderColorDto();

			BeanUtils.copyProperties(e, orderColorDto);
			List<OrderSizeDo> orderSizeDos = orderSizeMapper.selectList(sizeDoLambdaQueryWrapper);
			orderColorDto.setOrderSizes(orderSizeDos);
			return orderColorDto;
		}).collect(Collectors.toList());

		WorkOrderVo workOrderVo = new WorkOrderVo();
		BeanUtils.copyProperties(workOrder, workOrderVo);
		workOrderVo.setOrderColors(collect);
		return workOrderVo;

	}


	public void updateFabric(String tag, Long workOrderId, List<OrderFabricDto> orderFabricDtos) {
		if (orderFabricDtos.isEmpty()) {
			return;
		}
		// 查询
		LambdaQueryWrapper<OrderFabricDo> orderFabricWrapper = Wrappers.lambdaQuery();
		orderFabricWrapper.eq(OrderFabricDo::getWorkOrderId, workOrderId)
				.eq(OrderFabricDo::getMaterialCategory, tag);
		List<OrderFabricDo> OrderFabricDos = orderFabricMapper.selectList(orderFabricWrapper);


		if (!OrderFabricDos.isEmpty()) {
			List<Long> list = OrderFabricDos.stream().map(OrderFabricDo::getId).toList();
			LambdaQueryWrapper<OrderFabricColorSizeDo> orderFabricColorSizeDo = Wrappers.lambdaQuery();
			orderFabricColorSizeDo.in(OrderFabricColorSizeDo::getOrderFabricId, list);
//			orderFabricColorSizMapper.delete(orderFabricColorSizeDo);
			orderFabricMapper.deleteByIds(list);
		}
		List<OrderFabricDo> orderFabricDos = orderFabricDtos.stream().map(e -> {
			OrderFabricDo orderFabricDo = new OrderFabricDo();
			BeanUtils.copyProperties(e, orderFabricDo);
//			orderFabricMapper.insert(orderFabricDo);
			return orderFabricDo;
//			List<OrderFabricColorSizeDo> result = new ArrayList<>();
//
//			if (orderFabricDo.getIsColorScheme() == 0 && orderFabricDo.getIsMatchingSize() == 0) {
//
//				List<FabricColorDto> fabricColorDtos = e.getFabricColorDtos();
//				List<FabricSizeDto> fabricSizeDtos = e.getFabricSizeDtos();
//
//				Map<Long, FabricColorDto> colorMap = fabricColorDtos.stream()
//						.collect(Collectors.toMap(FabricColorDto::getOrderColorId, Function.identity(), (a, b) -> a));
//				// 对尺寸集合进行流式处理，并合并颜色数据
//				 result = fabricSizeDtos.stream()
//						.map(sizeDto -> {
//							FabricColorDto colorDto = colorMap.get(sizeDto.getOrderColorId());
//							if (colorDto != null) {
//								OrderFabricColorSizeDo doObj = new OrderFabricColorSizeDo();
//								BeanUtils.copyProperties(colorDto, doObj); // 拷贝颜色数据
//								BeanUtils.copyProperties(sizeDto, doObj);  // 拷贝尺寸数据（可能会覆盖相同字段）
//								return doObj;
//							} else {
//								return null; // 如果找不到颜色信息，可以选择跳过或保留
//							}
//						})
//						.filter(Objects::nonNull) // 过滤掉 colorDto 为 null 的情况
//						.toList();
//			}
//			if(orderFabricDo.getIsColorScheme() == 0 && orderFabricDo.getIsMatchingSize() != 0){
//				List<FabricColorDto> fabricColorDtos = e.getFabricColorDtos();
//				result =	fabricColorDtos.stream().map(s->{
//					OrderFabricColorSizeDo doObj = new OrderFabricColorSizeDo();
//					BeanUtils.copyProperties(s, doObj); // 拷贝颜色数据
//					return doObj;
//				}).toList();
//			}
//
//			if(orderFabricDo.getIsColorScheme() != 0 && orderFabricDo.getIsMatchingSize() == 0){
//				List<FabricSizeDto> fabricSizeDto = e.getFabricSizeDtos();
//				result =	fabricSizeDto.stream().map(s->{
//					OrderFabricColorSizeDo doObj = new OrderFabricColorSizeDo();
//					BeanUtils.copyProperties(s, doObj); // 拷贝颜色数据
//					return doObj;
//				}).toList();
//			}
//
//			result.forEach(t -> {
//				t.setOrderFabricId(id);
//				t.setWorkOrderId(workOrderId);
//				orderFabricColorSizMapper.insert(t);
//			});
		}).toList();
		orderFabricMapper.insert(orderFabricDos);
	}


	public void updateOrderPart(Long workOrderId, List<OrderPartDto> orderPartDtos) {

		LambdaQueryWrapper<OrderPartDo> orderPartDoWrapper = Wrappers.lambdaQuery();
		orderPartDoWrapper.eq(OrderPartDo::getWorkOrderId, workOrderId);
		List<OrderPartDo> orderPartDos = orderPartMapper.selectList(orderPartDoWrapper);
		if (!orderPartDos.isEmpty()) {
			orderPartMapper.delete(orderPartDoWrapper);
			LambdaQueryWrapper<OrderPartSizeDo> orderPartSizeDo = Wrappers.lambdaQuery();
			orderPartSizeDo.eq(OrderPartSizeDo::getWorkOrderId, workOrderId);
			orderPartSizeMapper.delete(orderPartSizeDo);
		}


		orderPartDtos.forEach(e -> {
			OrderPartDo orderPartDo = new OrderPartDo();
			BeanUtils.copyProperties(e, orderPartDo);
			orderPartMapper.insert(orderPartDo);
			Long id = orderPartDo.getId();
			List<OrderPartSizeDo> orderPartSize = e.getOrderPartSize();

			orderPartSize.forEach(s -> {

				s.setOrderPartId(id);
				s.setWorkOrderId(workOrderId);
			});

			orderPartSizeMapper.insert(orderPartSize);

		});

	}


	public void updateOrderProcess(Long workOrderId, OrderProcessDo orderProcessDos) {
		orderProcessDos.setWorkOrderId(workOrderId);
		orderProcessMapper.insertOrUpdate(orderProcessDos);
	}

	WorkOrderVo getFabric(String tag, WorkOrderDo workOrder) {
		WorkOrderVo workOrderVo = new WorkOrderVo();
		BeanUtils.copyProperties(workOrder, workOrderVo);

		LambdaQueryWrapper<OrderFabricDo> orderFabricWrapper = Wrappers.lambdaQuery();
		orderFabricWrapper.eq(OrderFabricDo::getWorkOrderId, workOrder.getId()).orderByDesc(OrderFabricDo::getCreateTime).eq(OrderFabricDo::getMaterialCategory, tag);
		List<OrderFabricDo> orderFabricDos = orderFabricMapper.selectList(orderFabricWrapper);

		if (orderFabricDos.isEmpty()) {
			return workOrderVo;
		}

		List<OrderFabricVo> list = orderFabricDos.stream().map(e -> {
			OrderFabricVo orderFabricDto = new OrderFabricVo();
			BeanUtils.copyProperties(e, orderFabricDto);
			if (orderFabricDto.getIsColorScheme() == 1 && orderFabricDto.getIsMatchingSize() == 1) {
				return orderFabricDto;
			}
			LambdaQueryWrapper<OrderFabricColorSizeDo> orderFabricColorSizeDoWrapper = Wrappers.lambdaQuery();
			orderFabricColorSizeDoWrapper.eq(OrderFabricColorSizeDo::getOrderFabricId, e.getId());
			List<OrderFabricColorSizeDo> orderFabricColorSizeDos = orderFabricColorSizMapper.selectList(orderFabricColorSizeDoWrapper);
			orderFabricDto.setOrderFabricColorSizeDos(orderFabricColorSizeDos);
			return orderFabricDto;
		}).toList();
		workOrderVo.setOrderFabricVo(list);
		return workOrderVo;
	}

	WorkOrderVo getOrderPart(String tag, WorkOrderDo workOrderDo) {
		WorkOrderVo workOrderVo = new WorkOrderVo();
		BeanUtils.copyProperties(workOrderDo, workOrderVo);

		Long id = workOrderDo.getId();

		LambdaQueryWrapper<OrderPartDo> orderPartDo = Wrappers.lambdaQuery();
		orderPartDo.eq(OrderPartDo::getWorkOrderId, id);
		List<OrderPartDo> orderPartDos = orderPartMapper.selectList(orderPartDo);

		LambdaQueryWrapper<OrderPartSizeDo> orderPartSizeDoWrapper = Wrappers.lambdaQuery();
		orderPartSizeDoWrapper.eq(OrderPartSizeDo::getWorkOrderId, id);
		List<OrderPartSizeDo> orderPartSizeDos = orderPartSizeMapper.selectList(orderPartSizeDoWrapper);

		Map<Long, List<OrderPartSizeDo>> map = orderPartSizeDos.stream()
				.collect(Collectors.groupingBy(OrderPartSizeDo::getOrderPartId));

		List<OrderPartDto> list = orderPartDos.stream().map(e -> {
			OrderPartDto orderPartDto = new OrderPartDto();
			BeanUtils.copyProperties(e, orderPartDto);
			List<OrderPartSizeDo> orderPartSizeDos1 = map.get(e.getId());
			orderPartDto.setOrderPartSize(orderPartSizeDos1);
			return orderPartDto;
		}).toList();

		workOrderVo.setOrderPartDtos(list);

		return workOrderVo;
	}

	WorkOrderVo getOrderProcess(WorkOrderDo workOrder) {

		LambdaQueryWrapper<OrderProcessDo> orderProcessWrapper = Wrappers.lambdaQuery();
		orderProcessWrapper.eq(OrderProcessDo::getWorkOrderId, workOrder.getId());
		OrderProcessDo orderProcessDo = orderProcessMapper.selectOne(orderProcessWrapper);
		WorkOrderVo workOrderVo = new WorkOrderVo();
		BeanUtils.copyProperties(workOrder, workOrderVo);
		workOrderVo.setOrderProcessDos(orderProcessDo);
		return workOrderVo;
	}


}
