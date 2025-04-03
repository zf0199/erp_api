package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.MaterialDo;
import com.jinpus.tpms.service.MaterialService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * 物料表
 *
 * @author pig
 * @date 2025-03-20 19:58:48
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/material")
@Tag(description = "material", name = "物料表管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MaterialController {

	private final MaterialService materialService;

	/**
	 * 分页查询
	 *
	 * @param page     分页对象
	 * @param material 物料表
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
//    @HasPermission("basic_material_view")
	public R getMaterialPage(@ParameterObject Page page, @ParameterObject MaterialDo material) {
		LambdaQueryWrapper<MaterialDo> wrapper = Wrappers.lambdaQuery();
		Optional.ofNullable(material)
				.ifPresent(t ->
						wrapper.like( ObjectUtils.isNotEmpty(material.getName()),MaterialDo::getName, material.getName())
								.or()
								.like( ObjectUtils.isNotEmpty(material.getName()),MaterialDo::getNo, material.getName())
								.eq(ObjectUtils.isNotEmpty(material.getMaterialTypeId()),MaterialDo::getMaterialTypeId,t.getMaterialTypeId())
								.orderByDesc(MaterialDo::getCreateTime)
				);
		return R.ok(materialService.page(page, wrapper));
	}


	/**
	 * 通过条件查询物料表
	 *
	 * @param material 查询条件
	 * @return R  对象列表
	 */
	@Operation(summary = "通过条件查询", description = "通过条件查询对象")
	@GetMapping("/details")
//    @HasPermission("basic_material_view")
	public R getDetails(@ParameterObject MaterialDo material) {
		return R.ok(materialService.list(Wrappers.query(material)));
	}

	/**
	 * 新增物料表
	 *
	 * @param material 物料表
	 * @return R
	 */
	@Operation(summary = "新增物料表", description = "新增物料表")
	@SysLog("新增物料表")
	@PostMapping
//    @HasPermission("basic_material_add")
	public R save(@RequestBody MaterialDo material) {
		return R.ok(materialService.save(material));
	}

	/**
	 * 修改物料表
	 *
	 * @param material 物料表
	 * @return R
	 */
	@Operation(summary = "修改物料表", description = "修改物料表")
	@SysLog("修改物料表")
	@PutMapping
//    @HasPermission("basic_material_edit")
	public R updateById(@RequestBody MaterialDo material) {
		return R.ok(materialService.updateById(material));
	}

	/**
	 * 通过id删除物料表
	 *
	 * @param ids id列表
	 * @return R
	 */
	@Operation(summary = "通过id删除物料表", description = "通过id删除物料表")
	@SysLog("通过id删除物料表")
	@DeleteMapping
//    @HasPermission("basic_material_del")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(materialService.removeBatchByIds(CollUtil.toList(ids)));
	}


	/**
	 * 导出excel 表格
	 *
	 * @param material 查询条件
	 * @param ids      导出指定ID
	 * @return excel 文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
//    @HasPermission("basic_material_export")
	public List<MaterialDo> exportExcel(MaterialDo material, Long[] ids) {
		return materialService.list(Wrappers.lambdaQuery(material).in(ArrayUtil.isNotEmpty(ids), MaterialDo::getId, ids));
	}

	/**
	 * 导入excel 表
	 *
	 * @param materialList  对象实体列表
	 * @param bindingResult 错误信息列表
	 * @return ok fail
	 */
	@PostMapping("/import")
//    @HasPermission("basic_material_export")
	public R importExcel(@RequestExcel List<MaterialDo> materialList, BindingResult bindingResult) {
		return R.ok(materialService.saveBatch(materialList));
	}
}
