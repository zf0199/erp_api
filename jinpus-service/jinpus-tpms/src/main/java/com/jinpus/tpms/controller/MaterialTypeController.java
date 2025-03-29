package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.MaterialTypeDo;
import com.jinpus.tpms.service.MaterialTypeService;
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
 * 物料类型表
 *
 * @author pig
 * @date 2025-03-20 19:59:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/materialType")
@Tag(description = "materialType", name = "物料类型表管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MaterialTypeController {

	private final MaterialTypeService materialTypeService;

	/**
	 * 分页查询
	 *
	 * @param page         分页对象
	 * @param materialType 物料类型表
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
//    @HasPermission("basic_materialType_view")
	public R getMaterialTypePage(@ParameterObject Page page, @ParameterObject MaterialTypeDo materialType) {
		LambdaQueryWrapper<MaterialTypeDo> wrapper = Wrappers.lambdaQuery();
		Optional.ofNullable(materialType)
				.filter(c -> ObjectUtils.isNotEmpty(materialType.getName()))
				.ifPresent(t ->
						wrapper.like(MaterialTypeDo::getTypeCode, materialType.getName())
								.or()
								.like(MaterialTypeDo::getName, materialType.getName())
				);
		return R.ok(materialTypeService.page(page, wrapper));
	}


	/**
	 * 通过条件查询物料类型表
	 *
	 * @param materialType 查询条件
	 * @return R  对象列表
	 */
	@Operation(summary = "通过条件查询", description = "通过条件查询对象")
	@GetMapping("/details")
//    @HasPermission("basic_materialType_view")
	public R getDetails(@ParameterObject MaterialTypeDo materialType) {
		return R.ok(materialTypeService.list(Wrappers.query(materialType)));
	}

	/**
	 * 新增物料类型表
	 *
	 * @param materialType 物料类型表
	 * @return R
	 */
	@Operation(summary = "新增物料类型表", description = "新增物料类型表")
	@SysLog("新增物料类型表")
	@PostMapping
//    @HasPermission("basic_materialType_add")
	public R save(@RequestBody MaterialTypeDo materialType) {
		return R.ok(materialTypeService.save(materialType));
	}

	/**
	 * 修改物料类型表
	 *
	 * @param materialType 物料类型表
	 * @return R
	 */
	@Operation(summary = "修改物料类型表", description = "修改物料类型表")
	@SysLog("修改物料类型表")
	@PutMapping
//    @HasPermission("basic_materialType_edit")
	public R updateById(@RequestBody MaterialTypeDo materialType) {
		return R.ok(materialTypeService.updateById(materialType));
	}

	/**
	 * 通过id删除物料类型表
	 *
	 * @param ids id列表
	 * @return R
	 */
	@Operation(summary = "通过id删除物料类型表", description = "通过id删除物料类型表")
	@SysLog("通过id删除物料类型表")
	@DeleteMapping
//    @HasPermission("basic_materialType_del")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(materialTypeService.removeBatchByIds(CollUtil.toList(ids)));
	}


	/**
	 * 导出excel 表格
	 *
	 * @param materialType 查询条件
	 * @param ids          导出指定ID
	 * @return excel 文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
//    @HasPermission("basic_materialType_export")
	public List<MaterialTypeDo> exportExcel(MaterialTypeDo materialType, Long[] ids) {
		return materialTypeService.list(Wrappers.lambdaQuery(materialType).in(ArrayUtil.isNotEmpty(ids), MaterialTypeDo::getId, ids));
	}

	/**
	 * 导入excel 表
	 *
	 * @param materialTypeList 对象实体列表
	 * @param bindingResult    错误信息列表
	 * @return ok fail
	 */
	@PostMapping("/import")
//    @HasPermission("basic_materialType_export")
	public R importExcel(@RequestExcel List<MaterialTypeDo> materialTypeList, BindingResult bindingResult) {
		return R.ok(materialTypeService.saveBatch(materialTypeList));
	}
}
