package com.jps.tpms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jps.tpms.api.domain.OrderProcedureDo;
import com.jps.tpms.api.dto.OrderProcedureDto;
import com.jps.tpms.service.OrderProcedureService;
import com.jps.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @className: OrderProcedureController
 * @author: zf
 * @date: 2025/5/5 19:06
 * @version: 1.0
 * @description: 工序工价
 */

/**
 *  工序工价
 */
@RestController
@AllArgsConstructor
public class OrderProcedureController {

	private final OrderProcedureService orderProcedureService;


	/**
	 *    分页查询
	 * @param page 分页参数
	 * @param orderProcedureDo 条件参数
	 * @return 查询结果
	 */
	@RequestMapping(value = "/orderProcedure/page",method = GET)
	public R getOrderProcedurePage(@ParameterObject Page<OrderProcedureDo> page, @ParameterObject OrderProcedureDo orderProcedureDo){

		LambdaQueryWrapper<OrderProcedureDo> orderProcedureDoWrapper = Wrappers.lambdaQuery();
		orderProcedureDoWrapper.orderByDesc(OrderProcedureDo::getCreateTime);
		return R.ok(orderProcedureService.getPage(page,orderProcedureDo));
	}

//
//	/**
//	 *    修改工序工价
//	 * @return 查询结果
//	 */
//	@RequestMapping(value = "/orderProcedure/page",method = GET)
//	public R updateOrderProcedurePage(){
//		return R.ok();
//	}

	/**
	 *    查看工序工价详情
	 * @return 查询结果
	 */
	@RequestMapping(value = "/orderProcedure",method = GET)
	public R getDetails(@RequestParam("id") Long id){
		return R.ok(orderProcedureService.getDetail(id));
	}


	/**
	 *   修改工序工价详情
	 * @return 查询结果
	 */
	@RequestMapping(value = "/orderProcedure",method = PUT)
	public R update(@RequestBody OrderProcedureDto orderProcedureDto){
		orderProcedureService.update(orderProcedureDto);
		return R.ok();
	}

	/**
	 *  获取工序进度
	 * @return
	 */
	@RequestMapping(value = "/orderProcedure/getProcedureProgress/{workId}",method = GET)
	public R getProcedureProgress(@PathVariable("workId") Long workId){
		return R.ok(orderProcedureService.getProcedureProgress(workId));

	}
}
