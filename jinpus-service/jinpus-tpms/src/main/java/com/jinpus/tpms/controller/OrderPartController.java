package com.jinpus.tpms.controller;

import com.jinpus.tpms.api.domain.OrderFabricDo;
import com.jinpus.tpms.api.domain.OrderPartDo;
import com.jinpus.tpms.api.dto.OrderPartDto;
import com.jinpus.tpms.service.OrderPartService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
