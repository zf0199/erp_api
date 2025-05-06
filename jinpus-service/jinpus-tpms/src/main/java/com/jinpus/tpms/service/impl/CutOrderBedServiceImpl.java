package com.jinpus.tpms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.*;
import com.jinpus.tpms.api.dto.CutOrderBedDto;
import com.jinpus.tpms.api.dto.CutOrderColorDto;
import com.jinpus.tpms.api.dto.CutOrderSizeDto;
import com.jinpus.tpms.api.vo.CutBedSumVo;
import com.jinpus.tpms.api.vo.CutOrderBedColorSizeVo;
import com.jinpus.tpms.api.vo.CutOrderBedVo;
import com.jinpus.tpms.mapper.*;
import com.jinpus.tpms.service.CutOrderBedService;
import com.jinpus.tpms.service.CutOrderColorService;
import com.jinpus.tpms.service.CutOrderSizeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @className: CutOrderBedServiceImpl
 * @author: zf
 * @date: 2025/4/28 16:08
 * @version: 1.0
 * @description:
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CutOrderBedServiceImpl extends ServiceImpl<CutOrderBedMapper, CutOrderBedDo> implements CutOrderBedService {

	private final CutOrderColorService cutOrderColorService;

	private final CutOrderSizeService cutOrderSizeService;

	private final CutOrderColorMapper cutOrderColorMapper;

	private final CutOrderSizeMapper cutOrderSizeMapper;

	private final CutOrderBedMapper cutOrderBedMapper;

	private final OrderProcedureMapper orderProcedureMapper;


	@Override
	public void saveCutOrderBed(CutOrderBedDto cutOrderBedDto) {


		CutOrderBedDo cutOrderBedDo = new CutOrderBedDo();
		BeanUtils.copyProperties(cutOrderBedDto, cutOrderBedDo);
		this.save(cutOrderBedDo);


		Long cutOrderBedId = cutOrderBedDo.getId();
		List<CutOrderColorDto> cutOrderColorDos = cutOrderBedDto.getCutOrderColorDtos();

		cutOrderColorDos.forEach(e -> {
			CutOrderColorDo cutOrderColorDo = new CutOrderColorDo();
			BeanUtils.copyProperties(e, cutOrderColorDo);
			cutOrderColorDo.setCutOrderBedId(cutOrderBedId);
			cutOrderColorService.save(cutOrderColorDo);
			Long cutColorId = cutOrderColorDo.getId();
			List<CutOrderSizeDo> cutOrderSizeDos = e.getCutOrderSizeDos();
			cutOrderSizeDos.forEach(t -> {
				t.setCutOrderId(cutOrderBedDto.getCutOrderId());
				t.setCutColorId(cutColorId);
				t.setCutOrderBedId(cutOrderBedId);
			});
			cutOrderSizeService.saveBatch(cutOrderSizeDos);
		});
		getCutNumAndBedNum(cutOrderBedDto.getCutOrderId());


//		int startNum = maxSort + 1; // 自定义开始数字
//		int totalGroups = cutOrderColorDos.size();
//
//		AtomicInteger oddBundleNum = new AtomicInteger(startNum);      // 奇数组，从 startNum 开始
//		AtomicInteger evenBundleNum = new AtomicInteger(startNum + 1); // 偶数组，从 startNum + 1 开始
//		AtomicInteger maxUsedBundleNum = new AtomicInteger(startNum - 1); // 记录最大使用过的编号
//		AtomicInteger groupIndex = new AtomicInteger(0); // 当前是第几组
//
//		cutOrderColorDos.forEach(e -> {
//			int currentGroup = groupIndex.getAndIncrement();
//			boolean isLastGroup = (currentGroup == totalGroups - 1);
//			boolean isOddGroup = currentGroup % 2 == 0;
//
//			// 创建并保存颜色数据
//			CutOrderColorDo cutOrderColorDo = new CutOrderColorDo();
//			BeanUtils.copyProperties(e, cutOrderColorDo);
//			cutOrderColorDo.setCutOrderBedId(cutOrderBedId);
//			cutOrderColorService.save(cutOrderColorDo);
//			Long cutColorId = cutOrderColorDo.getId();
//
//			List<CutOrderSizeDo> cutOrderSizeDos = e.getCutOrderSizeDos();
//
//			if (isLastGroup && totalGroups % 2 == 1) {
//				// 奇数总组数的最后一组，使用 +1 自增
//				AtomicInteger lastCounter = new AtomicInteger(maxUsedBundleNum.get() + 1);
//				cutOrderSizeDos.forEach(t -> {
//					t.setCutOrderId(cutOrderBedDto.getCutOrderId());
//					t.setCutColorId(cutColorId);
//					t.setCutOrderBedId(cutOrderBedId);
//					int bundleNum = lastCounter.getAndIncrement();
//					t.setBundleNum(bundleNum);
//					maxUsedBundleNum.set(bundleNum);
//				});
//			} else {
//				// 偶数组或奇数组，使用 +2 自增
//				AtomicInteger counter = isOddGroup ? oddBundleNum : evenBundleNum;
//				cutOrderSizeDos.forEach(t -> {
//					t.setCutOrderId(cutOrderBedDto.getCutOrderId());
//					t.setCutColorId(cutColorId);
//					t.setCutOrderBedId(cutOrderBedId);
//					int bundleNum = counter.getAndAdd(2);
//					t.setBundleNum(bundleNum);
//					maxUsedBundleNum.set(Math.max(maxUsedBundleNum.get(), bundleNum));
//				});
//			}
//
//			cutOrderSizeService.saveBatch(cutOrderSizeDos);
//		});


	}

	@Override
	public void deleteCutOrderBed(Long id) {
		CutOrderBedDo byId = this.getById(id);
		Long cutOrderId = byId.getCutOrderId();
		this.removeById(id);

		// 删除裁床尺寸
		LambdaQueryWrapper<CutOrderSizeDo> cutOrderSizeDoWrapper = Wrappers.lambdaQuery();
		cutOrderSizeDoWrapper.eq(CutOrderSizeDo::getCutOrderBedId, id);
		cutOrderSizeService.remove(cutOrderSizeDoWrapper);

		//删除裁床颜色
		LambdaQueryWrapper<CutOrderColorDo> cutOrderColorDoWrapper = Wrappers.lambdaQuery();
		cutOrderColorDoWrapper.eq(CutOrderColorDo::getCutOrderBedId, id);
		cutOrderColorService.remove(cutOrderColorDoWrapper);

		getCutNumAndBedNum(cutOrderId);

	}

	@Override
	public void updateCutOrderBed(CutOrderBedDto cutOrderBedDto) {


		Long id = cutOrderBedDto.getId();

		CutOrderBedDo cutOrderBedDo = new CutOrderBedDo();
		BeanUtils.copyProperties(cutOrderBedDto, cutOrderBedDo);
		this.updateById(cutOrderBedDo);

		// 删除裁床尺寸
		LambdaQueryWrapper<CutOrderSizeDo> cutOrderSizeDoWrapper1 = Wrappers.lambdaQuery();
		cutOrderSizeDoWrapper1.eq(CutOrderSizeDo::getCutOrderBedId, id);
		cutOrderSizeService.remove(cutOrderSizeDoWrapper1);

		//删除裁床颜色
		LambdaQueryWrapper<CutOrderColorDo> cutOrderColorDoWrapper = Wrappers.lambdaQuery();
		cutOrderColorDoWrapper.eq(CutOrderColorDo::getCutOrderBedId, id);
		cutOrderColorService.remove(cutOrderColorDoWrapper);

		List<CutOrderColorDto> cutOrderColorDtos = cutOrderBedDto.getCutOrderColorDtos();

		cutOrderColorDtos.stream().forEach(e -> {
			CutOrderColorDo cutOrderColorDo = new CutOrderColorDo();
			BeanUtils.copyProperties(e, cutOrderColorDo);
			cutOrderColorDo.setCutOrderBedId(id);
			cutOrderColorService.save(cutOrderColorDo);
			Long cutColorId = cutOrderColorDo.getId();
			List<CutOrderSizeDo> cutOrderSizeDos = e.getCutOrderSizeDos();
		cutOrderSizeDos.forEach(t -> {
//				t.setBundleNum();
				t.setCutOrderId(cutOrderBedDto.getCutOrderId());
				t.setCutColorId(cutColorId);
				t.setCutOrderBedId(id);
			});
			cutOrderSizeService.saveBatch(cutOrderSizeDos);
		});
		CutBedSumVo cutNumAndBedNum = getCutNumAndBedNum(cutOrderBedDto.getCutOrderId());

//		int startNum = maxSort + 1; // 自定义开始数字
//		int totalGroups = cutOrderColorDtos.size();
//
//		AtomicInteger oddBundleNum = new AtomicInteger(startNum);      // 奇数组，从 startNum 开始
//		AtomicInteger evenBundleNum = new AtomicInteger(startNum + 1); // 偶数组，从 startNum + 1 开始
//		AtomicInteger maxUsedBundleNum = new AtomicInteger(startNum - 1); // 记录最大使用过的编号
//		AtomicInteger groupIndex = new AtomicInteger(0); // 当前是第几组
//
//		cutOrderColorDtos.forEach(e -> {
//			int currentGroup = groupIndex.getAndIncrement();
//			boolean isLastGroup = (currentGroup == totalGroups - 1);
//			boolean isOddGroup = currentGroup % 2 == 0;
//
//			// 创建并保存颜色数据
//			CutOrderColorDo cutOrderColorDo = new CutOrderColorDo();
//			BeanUtils.copyProperties(e, cutOrderColorDo);
//			cutOrderColorDo.setCutOrderBedId(id);
//			cutOrderColorService.save(cutOrderColorDo);
//			Long cutColorId = cutOrderColorDo.getId();
//
//			List<CutOrderSizeDo> cutOrderSizeDos = e.getCutOrderSizeDos();
//
//			if (isLastGroup && totalGroups % 2 == 1) {
//				// 奇数总组数的最后一组，使用 +1 自增
//				AtomicInteger lastCounter = new AtomicInteger(maxUsedBundleNum.get() + 1);
//				cutOrderSizeDos.forEach(t -> {
//					t.setCutOrderId(cutOrderBedDto.getCutOrderId());
//					t.setCutColorId(cutColorId);
//					t.setCutOrderBedId(id);
//					int bundleNum = lastCounter.getAndIncrement();
//					t.setBundleNum(bundleNum);
//					maxUsedBundleNum.set(bundleNum);
//				});
//			} else {
//				// 偶数组或奇数组，使用 +2 自增
//				AtomicInteger counter = isOddGroup ? oddBundleNum : evenBundleNum;
//				cutOrderSizeDos.forEach(t -> {
//					t.setCutOrderId(cutOrderBedDto.getCutOrderId());
//					t.setCutColorId(cutColorId);
//					t.setCutOrderBedId(id);
//					int bundleNum = counter.getAndAdd(2);
//					t.setBundleNum(bundleNum);
//					maxUsedBundleNum.set(Math.max(maxUsedBundleNum.get(), bundleNum));
//				});
//			}
//			cutOrderSizeService.saveBatch(cutOrderSizeDos);
//		});

	}

	@Override
	public CutOrderBedVo getCutOrderBed(Long id) {

		CutOrderBedVo cutOrderBedVo = new CutOrderBedVo();
		CutOrderBedDo byId = this.getById(id);

		if (byId == null) {
			return null;
		}
		BeanUtils.copyProperties(byId, cutOrderBedVo);


		LambdaQueryWrapper<CutOrderColorDo> cutOrderColorDoWrapper = Wrappers.lambdaQuery();
		cutOrderColorDoWrapper.eq(CutOrderColorDo::getCutOrderBedId, id);

		List<CutOrderColorDo> cutOrderColorDos = cutOrderColorMapper.selectList(cutOrderColorDoWrapper);

		List<CutOrderColorDto> list = cutOrderColorDos.stream().map(e -> {

			CutOrderColorDto cutOrderColorDto = new CutOrderColorDto();
			Long id1 = e.getId();

			BeanUtils.copyProperties(e, cutOrderColorDto);

			LambdaQueryWrapper<CutOrderSizeDo> cutOrderSizeDoWrapper = Wrappers.lambdaQuery();
			cutOrderSizeDoWrapper.eq(CutOrderSizeDo::getCutColorId, id1);
			List<CutOrderSizeDo> cutOrderSizeDos = cutOrderSizeMapper.selectList(cutOrderSizeDoWrapper);
			cutOrderColorDto.setCutOrderSizeDos(cutOrderSizeDos);
			return cutOrderColorDto;
		}).toList();
		cutOrderBedVo.setCutOrderColorDto(list);
		return cutOrderBedVo;
	}


	@Override
	public List<CutOrderBedColorSizeVo> getCutOrderBedBundle(Long id) {

		LambdaQueryWrapper<CutOrderColorDo> cutOrderColorDoWrapper = Wrappers.lambdaQuery();
		cutOrderColorDoWrapper.eq(CutOrderColorDo::getCutOrderBedId, id);

		List<CutOrderColorDo> cutOrderColorDos = cutOrderColorMapper.selectList(cutOrderColorDoWrapper);

		List<Long> ids = cutOrderColorDos.stream().map(CutOrderColorDo::getId).toList();
		LambdaQueryWrapper<CutOrderSizeDo> cutOrderSizeDoWrapper = Wrappers.lambdaQuery();
		cutOrderSizeDoWrapper.in(CutOrderSizeDo::getCutColorId, ids);
		List<CutOrderSizeDo> cutOrderSizeDos = cutOrderSizeMapper.selectList(cutOrderSizeDoWrapper);

		Map<Long, CutOrderColorDo> collect = cutOrderColorDos.stream().collect(Collectors.toMap(CutOrderColorDo::getId, e -> e));
		// 2. 遍历 size 列表，构建 ColorSize
		List<CutOrderBedColorSizeVo> collect1 = cutOrderSizeDos.stream().map(size -> {
			CutOrderColorDo color = collect.get(size.getCutColorId());
			CutOrderBedColorSizeVo cutOrderBedColorSizeVo = new CutOrderBedColorSizeVo();
			BeanUtils.copyProperties(color, cutOrderBedColorSizeVo);
			BeanUtils.copyProperties(size, cutOrderBedColorSizeVo);
			return cutOrderBedColorSizeVo;
		}).collect(Collectors.toList());

		return collect1;

	}


	public CutBedSumVo getCutNumAndBedNum(Long id){
		LambdaQueryWrapper<CutOrderSizeDo>   cutOrderSizeDoWrapper = Wrappers.lambdaQuery();
		cutOrderSizeDoWrapper.eq(CutOrderSizeDo::getCutOrderId,id);

		int allCutSum = cutOrderSizeMapper.selectList(cutOrderSizeDoWrapper).stream()
				.map(CutOrderSizeDo::getSizeSumNum)
				.filter(Objects::nonNull)
				.mapToInt(Integer::intValue)
				.sum();

		LambdaQueryWrapper<CutOrderBedDo> cutOrderBedWrapper = Wrappers.lambdaQuery();
		cutOrderBedWrapper.eq(CutOrderBedDo::getCutOrderId,id);
		List<CutOrderBedDo> cutOrderBedDos = cutOrderBedMapper.selectList(cutOrderBedWrapper);

		LambdaUpdateWrapper<OrderProcedureDo> objectLambdaUpdateWrapper = Wrappers.lambdaUpdate();
		objectLambdaUpdateWrapper.eq(OrderProcedureDo::getCutOrderId,id)
				.set(OrderProcedureDo::getCutNum,allCutSum)
				.set(OrderProcedureDo::getBedNum,cutOrderBedDos.size());
		orderProcedureMapper.update(null,objectLambdaUpdateWrapper);

		CutBedSumVo cutBedSumVo = new CutBedSumVo();
		cutBedSumVo.setAllCutSun(allCutSum);
		cutBedSumVo.setAllBedSum(cutOrderBedDos.size());
		return  cutBedSumVo;
	}

	public void  f1(){

//		LambdaUpdateWrapper<OrderProcedureDo> objectLambdaUpdateWrapper = Wrappers.lambdaUpdate();
//		objectLambdaUpdateWrapper.eq(OrderProcedureDo::getCutOrderId,cutOrderBedDo.getCutOrderId())
//				.set(OrderProcedureDo::getCutNum,cutNumAndBedNum.getAllCutSun())
//				.set(OrderProcedureDo::getBedNum,cutNumAndBedNum.getAllBedSum());
	}

}
