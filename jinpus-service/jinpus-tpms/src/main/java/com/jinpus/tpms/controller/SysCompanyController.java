package com.jinpus.tpms.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.SysCompanyDo;
import com.jinpus.tpms.service.SysCompanyService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Get;
import org.apache.commons.lang3.ObjectUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @className: SysCompanyController
 * @author: zf
 * @date: 2025/5/14 16:03
 * @version: 1.0
 * @description:
 */

/**
 *    公司
 */
@RestController
@RequiredArgsConstructor
public class SysCompanyController {

	private  final SysCompanyService sysCompanyService;


	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param sysCompanyDo  .
	 * @return 分页数据
	 */
	@RequestMapping(value = "/company/page" ,method = GET)
    @PreAuthorize("@pms.hasPermission('tpms_sysCompany_view')" )
	public R getSysCompanyPage(@ParameterObject Page<SysCompanyDo> page, @ParameterObject SysCompanyDo sysCompanyDo) {
		LambdaQueryWrapper<SysCompanyDo> wrapper = Wrappers.lambdaQuery();
		Optional.ofNullable(sysCompanyDo).ifPresent(t->
				wrapper.like(ObjectUtils.isNotEmpty(t.getCompanyName()),SysCompanyDo::getCompanyName,t.getCompanyName())
		);
		return R.ok(sysCompanyService.page(page, wrapper));
	}


	/**
	 * 通过id查询公司
	 * @param id id
	 * @return R
	 */
	@Operation(summary = "通过id查询" , description = "通过id查询" )
	@RequestMapping(value = "/company/{id}",method = GET)
    @PreAuthorize("@pms.hasPermission('tpms_sysCompany_view')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(sysCompanyService.getById(id));
	}

	/**
	 * 新增公司
	 * @param sysCompanyDo 单位表
	 * @return R
	 */
	@SysLog("新增公司" )
	@RequestMapping(value = "/company",method = POST)
	@PreAuthorize("@pms.hasPermission('tpms_sysConpany_add')" )
	public R save(@RequestBody SysCompanyDo sysCompanyDo) {
		return R.ok(sysCompanyService.save(sysCompanyDo));
	}

	/**
	 * 修改单位表
	 * @param sysCompanyDo 单位表
	 * @return R
	 */
//	@Operation(summary = "修改单位表" , description = "修改单位表" )
	@SysLog("修改公司" )
	@RequestMapping(value = "company",method = PUT)
    @PreAuthorize("@pms.hasPermission('tpms_sysConpany_edit')" )
	public R updateById(@RequestBody SysCompanyDo sysCompanyDo) {
		return R.ok(sysCompanyService.updateById(sysCompanyDo));
	}

	/**
	 * 通过id删除单位表
	 * @param ids id列表
	 * @return R
	 */
//	@Operation(summary = "通过id删除单位表" , description = "通过id删除单位表" )
	@SysLog("通过id删除公司" )
	@RequestMapping(value = "company",method = DELETE)
	@PreAuthorize("@pms.hasPermission('sysCompany_delete')" )
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(sysCompanyService.removeBatchByIds(CollUtil.toList(ids)));
	}






}
