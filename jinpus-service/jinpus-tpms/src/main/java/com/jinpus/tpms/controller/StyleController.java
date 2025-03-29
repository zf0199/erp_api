package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.StyleDo;
import com.jinpus.tpms.api.dto.StyleDto;
import com.jinpus.tpms.service.StyleService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.pig4cloud.pig.common.security.annotation.HasPermission;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 款类表
 *
 * @author pig
 * @date 2025-03-20 20:01:21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/style")
@Tag(description = "style", name = "款类表管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class StyleController {

	private final StyleService styleService;

	/**
	 * 分页查询
	 *
	 * @param page  分页对象
	 * @param style 款类表
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
//	@HasPermission("basic_style_view")
	public R getStylePage(@ParameterObject Page page, @ParameterObject StyleDo style) {
		LambdaQueryWrapper<StyleDo> wrapper = Wrappers.lambdaQuery();
		return R.ok(styleService.page(page, wrapper));
	}


	/**
	 * 通过条件查询款类表
	 *
	 * @param style 查询条件
	 * @return R  对象列表
	 */
	@Operation(summary = "通过条件查询", description = "通过条件查询对象")
	@GetMapping("/details")
//	@HasPermission("basic_style_view")
	public R getDetails(@ParameterObject StyleDo style) {
		return R.ok(styleService.list(Wrappers.query(style)));
	}

	/**
	 * 新增款类表
	 * @param style 款类表
	 * @return R
	 */
	@Operation(summary = "新增款类表", description = "新增款类表")
	@SysLog("新增款类表")
	@PostMapping
//	@HasPermission("basic_style_add")
	public R save(@RequestBody StyleDto style) {
		styleService.add(style);
		return R.ok();
	}

	/**
	 * 修改款类表
	 *
	 * @param style 款类表
	 * @return R
	 */
	@Operation(summary = "修改款类表", description = "修改款类表")
	@SysLog("修改款类表")
	@PutMapping
//	@HasPermission("basic_style_edit")
	public R updateById(@RequestBody StyleDo style) {
		return R.ok(styleService.updateById(style));
	}

	/**
	 * 通过id删除款类表
	 *
	 * @param ids styleId列表
	 * @return R
	 */
	@Operation(summary = "通过id删除款类表", description = "通过id删除款类表")
	@SysLog("通过id删除款类表")
	@DeleteMapping
//	@HasPermission("basic_style_del")
	public R removeById(@RequestBody Long[] ids) {
		styleService.removeAll(ids);
		return R.ok();
	}


	/**
	 * 导出excel 表格
	 *
	 * @param style 查询条件
	 * @param ids   导出指定ID
	 * @return excel 文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
//	@HasPermission("basic_style_export")
	public List<StyleDo> exportExcel(StyleDo style, Long[] ids) {
		return styleService.list(Wrappers.lambdaQuery(style).in(ArrayUtil.isNotEmpty(ids), StyleDo::getId, ids));
	}

	/**
	 * 导入excel 表
	 *
	 * @param styleList     对象实体列表
	 * @param bindingResult 错误信息列表
	 * @return ok fail
	 */
	@PostMapping("/import")
//	@HasPermission("basic_style_export")
	public R importExcel(@RequestExcel List<StyleDo> styleList, BindingResult bindingResult) {
		return R.ok(styleService.saveBatch(styleList));
	}
}
