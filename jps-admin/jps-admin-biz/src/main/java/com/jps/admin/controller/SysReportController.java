package com.jps.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jps.admin.api.entity.SysReport;
import com.jps.admin.service.SysReportService;
import com.jps.common.core.util.R;
import com.jps.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

/**
 * @className: SysReport
 * @author: zf
 * @date: 2025/5/16 17:45
 * @version: 1.0
 * @description: 报表管理
 */

/**
 *   报表管理
 */
@RestController
@RequiredArgsConstructor
public class SysReportController {

	private final SysReportService sysReportService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param sysReport  .
	 * @return 分页数据
	 */
	@RequestMapping(value = "/report/page" ,method = GET)
//	@PreAuthorize("@pms.hasPermission('sys_report_view')" )
	public R getReportPage(@ParameterObject Page<SysReport> page, @ParameterObject SysReport sysReport) {
		return R.ok(sysReportService.getPage(page, sysReport));
	}


	/**
	 * 通过id查询报表
	 * @param id id
	 * @return R
	 */
	@Operation(summary = "通过id查询" , description = "通过id查询" )
	@RequestMapping(value = "/report/{id}",method = GET)
//	@PreAuthorize("@pms.hasPermission('sys_report_view')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(sysReportService.getById(id));
	}

	/**
	 * 新增表报
	 * @param sysReport 报表
	 * @return R
	 */
	@SysLog("新增报表" )
	@RequestMapping(value = "/report",method = POST)
//	@PreAuthorize("@pms.hasPermission('sys_report_add')" )
	public R save(@RequestBody SysReport sysReport) {
		return R.ok(sysReportService.save(sysReport));
	}

	/**
	 * 修改报表
	 * @param sysReport 单位表
	 * @return R
	 */
//	@Operation(summary = "修改单位表" , description = "修改单位表" )
	@SysLog("修改报表" )
	@RequestMapping(value = "report",method = PUT)
//	@PreAuthorize("@pms.hasPermission('tpms_sysConpany_edit')" )
	public R updateById(@RequestBody SysReport sysReport) {
		return R.ok(sysReportService.updateById(sysReport));
	}

	/**
	 * 通过id删除报表
	 * @param ids id列表
	 * @return R
	 */
//	@Operation(summary = "通过id删除单位表" , description = "通过id删除单位表" )
	@SysLog("通过id删除报表" )
	@RequestMapping(value = "report",method = DELETE)
//	@PreAuthorize("@pms.hasPermission('sysCompany_delete')" )
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(sysReportService.removeBatchByIds(CollUtil.toList(ids)));
	}
}





































