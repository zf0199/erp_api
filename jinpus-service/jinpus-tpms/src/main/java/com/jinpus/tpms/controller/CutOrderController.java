package com.jinpus.tpms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.CutOrderDo;
import com.jinpus.tpms.api.dto.CutOrderDto;
import com.jinpus.tpms.api.dto.CutOrderQueryDto;
import com.jinpus.tpms.service.CutOrderBedService;
import com.jinpus.tpms.service.CutOrderService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @className: CutOrderController
 * @author: zf
 * @date: 2025/4/28 9:13
 * @version: 1.0
 * @description: 裁床管理
 */

/**
 *   裁床管理
 */
@RestController
@RequestMapping
@AllArgsConstructor
public class CutOrderController {

	private final  CutOrderService cutOrderService;

	private final CutOrderBedService cutOrderBedService;


	/**
	 *    分页查询
	 * @param page 分页参数
	 * @param cutOrderDo 条件参数
	 * @return 查询结果
	 */
	@RequestMapping(value = "/cutOrder/page",method = GET)
	public R getCutOrderPage(@ParameterObject Page page, @ParameterObject CutOrderQueryDto cutOrderDo){
		return R.ok(cutOrderService.getPage(page,cutOrderDo));
	}


	/**
	 *   新增裁床单
	 * @param CutOrderDo 裁床单
	 * @return 新增结果
	 */
	@RequestMapping(value = "/cutOrder",method = RequestMethod.POST)
	public R saveBed(@RequestBody CutOrderDo CutOrderDo){
		return R.ok(cutOrderService.save(CutOrderDo));
	}


	/**
	 *   查看裁床单详情
	 * @param id 裁床单id
	 * @return 裁床数据
	 */
	@RequestMapping(value = "/cutOrder/details",method = GET)
	public R getCutOrderDetail(@RequestParam Long id){
		return R.ok(cutOrderService.getCutOrderDetail(id));
	}

	/**
	 *   更新裁床单 床数
	 * @param cutOrderDto 裁床单数据
	 * @return
	 */
	@RequestMapping(value = "/cutOrder",method = RequestMethod.PUT)
	public R update(@RequestBody CutOrderDto cutOrderDto){
		cutOrderService.updateCutOrder(cutOrderDto);
		return R.ok();
	}

	/**
	 *   数据统计
	 * @param id 裁床单id
	 * @return .
	 */
	@RequestMapping(value = "cutOrder/count",method = GET)
	public R getCutOrderStatCount(Long id){
		return R.ok(cutOrderService.getCutOrderStatCount(id));
	}


	/**
	 *   获取起始扎号
	 * @param cutOrderId 裁床单id
	 * @return 起始扎号
	 */
	@RequestMapping(value = "cutOrder/getBundleNum",method = GET)
	public R getBundleNum(Long cutOrderId){
		return R.ok(cutOrderService.getBundleNum(cutOrderId));
	}


}
