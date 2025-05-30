package com.jps.tpms.controller;

import com.jps.tpms.api.domain.OrderFabricDo;
import com.jps.tpms.api.dto.OrderFabricDto;
import com.jps.tpms.api.vo.FabricColorSizeVo;
import com.jps.tpms.service.OrderFabricService;
import com.jps.common.core.util.R;
import com.jps.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * @className: 制单物料表
 * @author: zf
 * @date: 2025/4/22 11:07
 * @version: 1.0
 * @description:
 */

@Description("制单物料表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/OrderFabric")
//@Tag(description = "workOrder" , name = "生产制单基础表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class OrderFacricController {

	private final OrderFabricService orderFabricService;


	/**
	 * 新增制单物料
	 *
	 * @param OrderFabricDo 生产制单物料
	 * @return R
	 */
	@SysLog("新增生产制单基础表")
	@PostMapping
//    @HasPermission("basic_workOrder_add")
	public R save(@RequestBody OrderFabricDo OrderFabricDo) {
		//新增制单物料
		orderFabricService.saveOrUpdate(OrderFabricDo);
		return R.ok(OrderFabricDo.getId());
	}


	/**
	 * 通过id删除生产制单物料
	 *
	 * @param id
	 * @return R
	 */
//	@Operation(summary = "通过id删除生产制单基础表" , description = "通过id删除生产制单基础表" )
	@SysLog("通过id删除生产制单基础表")
	@DeleteMapping
//    @HasPermission("basic_workOrder_del")
	public R removeById(@RequestParam Long id) {
		orderFabricService.deleteById(id);
		return R.ok();
	}


	/**
	 * 新增配色配码
	 *
	 * @param orderFabricDto 配色配码明细
	 * @return R
	 */
//	@Operation(summary = "通过id删除生产制单基础表" , description = "通过id删除生产制单基础表" )
	@SysLog("修改配色配码")
	@PutMapping
//    @HasPermission("basic_workOrder_del")
	public R updateFabricColorAndSize(@RequestBody OrderFabricDto orderFabricDto) {

		orderFabricService.updateFabricOne(orderFabricDto);
		return R.ok();

	}

	/**
	 * 查询制单配色配码
	 *
	 * @param id 制单物料id
	 * @return R
	 */
//	@Operation(summary = "通过id删除生产制单基础表" , description = "通过id删除生产制单基础表" )
	@SysLog("查询配色配码")
	@GetMapping
//    @HasPermission("basic_workOrder_del")
	public R<FabricColorSizeVo> selectFabricColorAndSize(@RequestParam("tag") String tag,@RequestParam("workOrderId") Long workOrderId,@RequestParam("id") Long id) {
		return R.ok(orderFabricService.selectFabricColorAndSize(tag,workOrderId,id));
	}
}
