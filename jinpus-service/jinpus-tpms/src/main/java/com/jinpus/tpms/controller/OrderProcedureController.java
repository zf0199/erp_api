package com.jinpus.tpms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.CutOrderDo;
import com.jinpus.tpms.api.domain.OrderProcedureDo;
import com.jinpus.tpms.service.OrderProcedureService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
	public R getOrderProcedurePage(@ParameterObject Page page, @ParameterObject OrderProcedureDo orderProcedureDo){
		return R.ok(orderProcedureService.getPage(page));
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





}
