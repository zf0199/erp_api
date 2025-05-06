package com.jinpus.tpms.controller;

import com.jinpus.tpms.api.domain.OrderProcedureDetailDo;
import com.jinpus.tpms.api.domain.UnitDo;
import com.jinpus.tpms.service.OrderProcedureDetailService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * @className: OrderProcedureDetailController
 * @author: zf
 * @date: 2025/5/6 13:37
 * @version: 1.0
 * @description:
 */

/**
 *   工序工价详情
 */
@RestController
@AllArgsConstructor
public class OrderProcedureDetailController {


	private final OrderProcedureDetailService orderProcedureDetailService;



	/**
	 * 新增 工序工价详情
	 * @param orderProcedureDetailDo 工序工价详情
	 * @return R
	 */
	@SysLog("新增单位表" )
	@PostMapping
	public R save(@RequestBody OrderProcedureDetailDo orderProcedureDetailDo) {
		return R.ok(orderProcedureDetailService.save(orderProcedureDetailDo));
	}


	/**
	 * 删除工序工价详情
	 * @param id 工序工价详情
	 * @return R
	 */
	@SysLog("新增单位表" )
	@RequestMapping(value = "/orderProcedureDetail",method = DELETE)
	public R delete(@RequestParam("id") Long id) {
		return R.ok(orderProcedureDetailService.removeById(id));
	}


	/**
	 * 修改工序工价详情
	 * @param orderProcedureDetailDo 工序工价详情
	 * @return R
	 */
	@SysLog("新增单位表" )
	@RequestMapping(value = "/orderProcedureDetail",method = PUT)
	public R update(@RequestBody  OrderProcedureDetailDo orderProcedureDetailDo) {
		return R.ok(orderProcedureDetailService.updateById(orderProcedureDetailDo));
	}

}
