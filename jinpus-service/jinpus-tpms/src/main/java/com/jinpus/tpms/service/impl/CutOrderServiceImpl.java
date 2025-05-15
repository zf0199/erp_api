package com.jinpus.tpms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.*;
import com.jinpus.tpms.api.vo.*;
import com.jinpus.tpms.mapper.*;
import com.jinpus.tpms.api.dto.CutOrderDto;
import com.jinpus.tpms.api.dto.CutOrderQueryDto;
import com.jinpus.tpms.service.CutOrderBedService;
import com.jinpus.tpms.service.CutOrderService;
import com.jinpus.tpms.service.WorkOrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @className: CutOrderServiceImpl
 * @author: zf
 * @date: 2025/4/28 9:23
 * @version: 1.0
 * @description:
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CutOrderServiceImpl extends ServiceImpl<CutOrderMapper, CutOrderDo> implements CutOrderService {

	private final WorkOrderService workOrderService;
	private final WorkOrderMapper workOrderMapper;

	private final CutOrderBedService cutOrderBedService;

	private final CutOrderBedMapper cutOrderBedMapper;

	private final CutOrderColorMapper cutOrderColorMapper;

	private final CutOrderSizeMapper cutOrderSizeMapper;


	@Override
	public Page getPage(Page page, CutOrderQueryDto cutOrderDo) {

//		List<WorkOrderDo> workOrderDos = workOrderMapper.selectList(Wrappers.lambdaQuery());
//		List<CutOrderDo> list1 = workOrderDos.stream().filter(e -> !e.getOrderNo().equals("B-01-E021") && !e.getOrderNo().equals("B-01-E022")).map(t -> {
//			CutOrderDo cutOrderDo1 = new CutOrderDo();
//			cutOrderDo1.setCutOrderNo(("c" + t.getOrderNo()).toUpperCase());
//			cutOrderDo1.setWorkOrderId(t.getId());
//			cutOrderDo1.setProgress(0);
//			return cutOrderDo1;
//		}).toList();
//		this.saveBatch(list1);

		LambdaQueryWrapper<CutOrderDo> cutOrderWrapper = Wrappers.lambdaQuery();
		cutOrderWrapper.orderByDesc(CutOrderDo::getCreateTime);

		Page page1 = this.page(page,cutOrderWrapper);
		List<CutOrderDo> records = page1.getRecords();

		List<Long> list1 = records.stream().map(CutOrderDo::getWorkOrderId).toList();
		Map<Long, CutOrderDo> cutOrderMap = records.stream().collect(Collectors.toMap(CutOrderDo::getWorkOrderId, e -> e));
		LambdaQueryWrapper<WorkOrderDo> workOrderDoWrapper = Wrappers.lambdaQuery();
		workOrderDoWrapper.in(WorkOrderDo::getId,list1)
				.like(ObjectUtils.isNotEmpty(cutOrderDo.getOrderNo()) ,WorkOrderDo::getOrderNo,cutOrderDo.getOrderNo())
				.or()
				.eq(ObjectUtils.isNotEmpty(cutOrderDo.getStyleId()),WorkOrderDo::getStyleId,cutOrderDo.getStyleId());

		List<WorkOrderDo> workOrderDos = workOrderMapper.selectList(workOrderDoWrapper);

		List<CutOrderVo> list2 = workOrderDos.stream().map(e -> {
			CutOrderVo cutOrderVo = new CutOrderVo();
			CutOrderDo cutOrderDo1 = cutOrderMap.get(e.getId());

			BeanUtils.copyProperties(cutOrderDo1, cutOrderVo);
			cutOrderVo.setWorkOrderDo(e);
			return cutOrderVo;
		}).toList();

//		List<CutOrderVo> list = records.stream().map(e -> {
//
//			WorkOrderDo byId = workOrderService.getById(e.getWorkOrderId());
//			CutOrderVo cutOrderVo = new CutOrderVo();
//			BeanUtils.copyProperties(e, cutOrderVo);
//			cutOrderVo.setWorkOrderDo(byId);
//			return cutOrderVo;
//		}).toList();

		return page1.setRecords(list2);
	}

	@Override
	public void updateCutOrder(CutOrderDto cutOrderDto) {

		CutOrderDo cutOrderDo = new CutOrderDo();
		BeanUtils.copyProperties(cutOrderDto,cutOrderDo);
		this.updateById(cutOrderDo);
		Long id = cutOrderDo.getId();


		// TAG updateCutOrder : 裁床数据注释
//		if (ObjectUtils.isNotEmpty(cutOrderDto.getCutOrderBedDto())){
//
//		// 新增裁床
//		CutOrderBedDto cutOrderBedDto = cutOrderDto.getCutOrderBedDto();
//		CutOrderBedDo cutOrderBedDo = new CutOrderBedDo();
//		BeanUtils.copyProperties(cutOrderBedDto,cutOrderBedDo);
//		cutOrderBedDo.setCutOrderId(id);
//		cutOrderBedDo.setWorkOrderId(cutOrderDto.getWorkOrderId());
//		cutOrderBedService.save(cutOrderBedDo);
//
//
//
//		Long cutOrderBedId = cutOrderBedDo.getId();
//
//		List<CutOrderColorDto> cutOrderColorDos = cutOrderBedDto.getCutOrderColorDtos();
//
//		cutOrderColorDos.stream().forEach(e->{
//			CutOrderColorDo cutOrderColorDo = new CutOrderColorDo();
//			BeanUtils.copyProperties(e,cutOrderColorDo);
//			cutOrderColorDo.setCutOrderBedId(cutOrderBedId);
//
//			cutOrderColorService.save(cutOrderColorDo);
//			Long cutColorId = cutOrderColorDo.getId();
//			List<CutOrderSizeDo> cutOrderSizeDos = e.getCutOrderSizeDos();
//			 cutOrderSizeDos.forEach(t -> {
//				t.setCutOrderId(id);
//				t.setCutColorId(cutColorId);
//			});
//			cutOrderSizeService.saveBatch(cutOrderSizeDos);
//		});
//		}
	}

	@Override
	public CutOrderVo getCutOrderDetail(Long cutOrderId){
		CutOrderDo cutOrderDo = this.getById(cutOrderId);
		Long workOrderId = cutOrderDo.getWorkOrderId();
		WorkOrderDo workOrderDo = workOrderService.getById(workOrderId);

		CutOrderVo cutOrderVo = new CutOrderVo();
		cutOrderVo.setWorkOrderDo(workOrderDo);

		LambdaQueryWrapper<CutOrderBedDo> cutOrderBedDoWrapper = Wrappers.lambdaQuery();
		cutOrderBedDoWrapper.eq(CutOrderBedDo::getCutOrderId,cutOrderId);
		List<CutOrderBedDo> cutOrderBedDos = cutOrderBedMapper.selectList(cutOrderBedDoWrapper);
		cutOrderVo.setCutOrderBedDo(cutOrderBedDos);
		return cutOrderVo;
	}

	@Override
	public CountCutOrderVo getCutOrderStatCount(Long id) {
		LambdaQueryWrapper<CutOrderBedDo> cutOrderBedDo = Wrappers.lambdaQuery();
		cutOrderBedDo.eq(CutOrderBedDo::getCutOrderId,id);

		List<CutOrderBedDo> cutOrderBedDos = cutOrderBedMapper.selectList(cutOrderBedDo);
		if (cutOrderBedDos.isEmpty()){
			CountCutOrderVo countCutOrderVo = new CountCutOrderVo();
			countCutOrderVo.setCountColorVos(Collections.emptyList());
			countCutOrderVo.setCutOrderBedVos(Collections.emptyList());
			countCutOrderVo.setCountColorSizeVo(Collections.emptyList());
		return countCutOrderVo;
		}
		//  所有的床数
		List<Long> list1 = cutOrderBedDos.stream().map(CutOrderBedDo::getId).toList();


		LambdaQueryWrapper<CutOrderColorDo> cutOrderColorDoWrapper = Wrappers.lambdaQuery();
		cutOrderColorDoWrapper.in(CollectionUtils.isNotEmpty(list1),CutOrderColorDo::getCutOrderBedId,list1);
		List<CutOrderColorDo> cutOrderColorDos = cutOrderColorMapper.selectList(cutOrderColorDoWrapper);

		List<CountColorSizeVo> list2 = cutOrderColorDos.stream()
				.flatMap(color -> {
					// 查找每个颜色下的所有尺码数据
					LambdaQueryWrapper<CutOrderSizeDo> queryWrapper = Wrappers.lambdaQuery();
					queryWrapper.eq(CutOrderSizeDo::getCutColorId, color.getId());
					List<CutOrderSizeDo> sizeList = cutOrderSizeMapper.selectList(queryWrapper);

					// 将颜色 + 尺码组装成 CountColorSizeVo
					return sizeList.stream().map(size -> {
						CountColorSizeVo vo = new CountColorSizeVo();
						vo.setColor(color.getColorName());
						vo.setSize(size.getSizeName());
						vo.setOrderNum(size.getOrderSizeNum());
						vo.setCutNum(size.getSizeSumNum());
						vo.setSort(size.getSort());
						return vo;
					});
				})
				.toList(); // 若是 JDK <16，则改为 .collect(Collectors.toList());


		List<CountColorSizeVo> list3 = new ArrayList<>(
				list2.stream()
						.collect(Collectors.toMap(
								vo -> vo.getColor() + "#" + vo.getSize(), // 用 color+size 作为唯一键
								vo -> vo,
								(vo1, vo2) -> {
									vo1.setCutNum(vo1.getCutNum() + vo2.getCutNum());
									vo1.setOrderNum(vo1.getOrderNum()); // 如果你也希望 orderNum 合并
									return vo1;
								}
						))
						.values()
		);

		list3.sort(Comparator
				.comparing(CountColorSizeVo::getColor)
				.thenComparing(CountColorSizeVo::getSort, Comparator.nullsFirst(Integer::compareTo))
		);

		//  所有裁床数据
		List<CutOrderBedVo> list = cutOrderBedDos.stream().map(e -> cutOrderBedService.getCutOrderBed(e.getId())).toList();

		CountCutOrderVo countCutOrderVo = new CountCutOrderVo();
		countCutOrderVo.setCutOrderBedVos(list);
		countCutOrderVo.setCountColorSizeVo(list3);

		// 按颜色分组
		Map<String, Map<String, CountColorSizeVo>> grouped = list3.stream()
				.collect(Collectors.groupingBy(
						CountColorSizeVo::getColor,
						Collectors.toMap(
								CountColorSizeVo::getSize,
								vo -> new CountColorSizeVo(vo.getColor(), vo.getSize(), vo.getOrderNum(), vo.getCutNum(), vo.getSort()),
								(vo1, vo2) -> {
									vo1.setOrderNum(vo1.getOrderNum());
									vo1.setCutNum(vo1.getCutNum() + vo2.getCutNum());
									return vo1;
								}
						)
				));
		// 转换为 CountColorVo 列表
		List<CountColorVo> result = new ArrayList<>();
		for (Map.Entry<String, Map<String, CountColorSizeVo>> entry : grouped.entrySet()) {
			String color = entry.getKey();
			List<CountSizeVo> sizes = entry.getValue().values().stream()
					.map(vo -> new CountSizeVo(vo.getSize(), vo.getOrderNum(), vo.getCutNum(), vo.getCutNum() - vo.getOrderNum() ))
					.collect(Collectors.toList());
			CountColorVo colorVo = new CountColorVo();
			colorVo.setColorName(color);
			colorVo.setCountSizeVos(sizes);
			result.add(colorVo);
		}
		countCutOrderVo.setCountColorVos(result);

		return countCutOrderVo;
	}


	public Integer getBundleNum(Long cutOrderId){
		LambdaQueryWrapper<CutOrderSizeDo> cutOrderSizeDoWrapper = Wrappers.lambdaQuery();
		cutOrderSizeDoWrapper.eq(CutOrderSizeDo::getCutOrderId, cutOrderId).isNotNull(CutOrderSizeDo::getBundleNum).select(CutOrderSizeDo::getBundleNum).orderByDesc(CutOrderSizeDo::getBundleNum).last("limit 1");
		return cutOrderSizeMapper.selectObjs(cutOrderSizeDoWrapper).stream().map(obj -> (Integer) obj).findFirst().orElse(1); // 没有记录时默认返回 0
	}
}
