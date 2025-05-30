package com.jps.tpms.controller;

/**
 * @className: CutOrderBedController
 * @author: zf
 * @date: 2025/4/28 19:26
 * @version: 1.0
 * @description:
 */

import com.jps.tpms.api.dto.CutOrderBedDto;
import com.jps.tpms.service.CutOrderBedService;
import com.jps.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;


/**
 *   裁床单床管理
 */

@RestController
@AllArgsConstructor
public class CutOrderBedController {


	private final CutOrderBedService cutOrderBedService;


	/**
	 *  新增裁床床数
	 * @param cutOrderBedDto 裁床数据
	 * @return .
	 */
	@RequestMapping(value = "/cutOrderBed",method = POST)
	public R  saveCutOrderBed(@RequestBody CutOrderBedDto cutOrderBedDto){
		cutOrderBedService.saveCutOrderBed(cutOrderBedDto);
		return R.ok();
	}


	/**
	 *   删除裁床床数
	 * @param id 床数id
	 * @return .
	 */
	@RequestMapping(value = "/cutOrderBed",method = DELETE)
	R deleteCutOrderBed(@RequestParam("id") Long id){
		cutOrderBedService.deleteCutOrderBed(id);

		return R.ok();
	}


	/**
	 *   修改裁床床数
	 * @param cutOrderBedDto 裁床床数数据
	 * @return .
	 */
	@RequestMapping(value = "/cutOrderBed",method = PUT)
	R updateCutOrderBed(@RequestBody  CutOrderBedDto cutOrderBedDto){
		cutOrderBedService.updateCutOrderBed(cutOrderBedDto);
		return R.ok();
	}


	/**
	 *   查询裁床床数据详情
	 * @param id 裁床床id
	 * @return .
	 */
	@RequestMapping(value = "/cutOrderBed",method = GET)
	R getCutOrderBed(@RequestParam("id") Long id){
		return R.ok(cutOrderBedService.getCutOrderBed(id));
	}

	/**
	 *   查询裁床床数据-分扎
	 * @param id 裁床床id
	 * @return .
	 */
	@RequestMapping(value = "/cutOrderBed/bundle",method = GET)
	R getCutOrderBedBundle(@RequestParam("id") Long id){
		return R.ok(cutOrderBedService.getCutOrderBedBundle(id));
	}




}
