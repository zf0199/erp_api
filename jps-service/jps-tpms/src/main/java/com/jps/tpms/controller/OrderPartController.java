package com.jps.tpms.controller;

import com.jps.tpms.service.OrderPartService;
import com.jps.common.core.util.R;
import com.jps.common.log.annotation.SysLog;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @className: OrderPartController
 * @author: zf
 * @date: 2025/4/23 15:56
 * @version: 1.0
 * @description: 制单部位尺寸
 */
@RestController
@RequiredArgsConstructor
public class OrderPartController {

	private final OrderPartService orderPartService;

	/**
	 * 通过id删除生产制单部位
	 *
	 * @param id 部位id
	 * @return R
	 */
	@SysLog("通过部位id删除生产制单部位和尺寸")
	@RequestMapping(value = "/orderPart",method = RequestMethod.DELETE)
//    @HasPermission("basic_workOrder_del")
	public R removeById(@RequestParam Long id) {
		orderPartService.deleteById(id);
		return R.ok();
	}



}
