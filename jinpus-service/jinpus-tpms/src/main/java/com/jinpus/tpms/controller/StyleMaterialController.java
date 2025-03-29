package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.StyleMaterialDo;
import com.jinpus.tpms.service.StyleMaterialService;
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
 * 款类默认物料表
 *
 * @author pig
 * @date 2025-03-20 20:01:52
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/styleMaterial" )
@Tag(description = "styleMaterial" , name = "款类默认物料表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class StyleMaterialController {

    private final StyleMaterialService styleMaterialService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param styleMaterial 款类默认物料表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @HasPermission("basic_styleMaterial_view")
    public R getStyleMaterialPage(@ParameterObject Page page, @ParameterObject StyleMaterialDo styleMaterial) {
        LambdaQueryWrapper<StyleMaterialDo> wrapper = Wrappers.lambdaQuery();
        return R.ok(styleMaterialService.page(page, wrapper));
    }


    /**
     * 通过条件查询款类默认物料表
     * @param styleMaterial 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
    @HasPermission("basic_styleMaterial_view")
    public R getDetails(@ParameterObject StyleMaterialDo styleMaterial) {
        return R.ok(styleMaterialService.list(Wrappers.query(styleMaterial)));
    }

    /**
     * 新增款类默认物料表
     * @param styleMaterial 款类默认物料表
     * @return R
     */
    @Operation(summary = "新增款类默认物料表" , description = "新增款类默认物料表" )
    @SysLog("新增款类默认物料表" )
    @PostMapping
    @HasPermission("basic_styleMaterial_add")
    public R save(@RequestBody StyleMaterialDo styleMaterial) {
        return R.ok(styleMaterialService.save(styleMaterial));
    }

    /**
     * 修改款类默认物料表
     * @param styleMaterial 款类默认物料表
     * @return R
     */
    @Operation(summary = "修改款类默认物料表" , description = "修改款类默认物料表" )
    @SysLog("修改款类默认物料表" )
    @PutMapping
    @HasPermission("basic_styleMaterial_edit")
    public R updateById(@RequestBody StyleMaterialDo styleMaterial) {
        return R.ok(styleMaterialService.updateById(styleMaterial));
    }

    /**
     * 通过id删除款类默认物料表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除款类默认物料表" , description = "通过id删除款类默认物料表" )
    @SysLog("通过id删除款类默认物料表" )
    @DeleteMapping
    @HasPermission("basic_styleMaterial_del")
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(styleMaterialService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param styleMaterial 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @HasPermission("basic_styleMaterial_export")
    public List<StyleMaterialDo> exportExcel(StyleMaterialDo styleMaterial, Long[] ids) {
        return styleMaterialService.list(Wrappers.lambdaQuery(styleMaterial).in(ArrayUtil.isNotEmpty(ids), StyleMaterialDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param styleMaterialList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
    @HasPermission("basic_styleMaterial_export")
    public R importExcel(@RequestExcel List<StyleMaterialDo> styleMaterialList, BindingResult bindingResult) {
        return R.ok(styleMaterialService.saveBatch(styleMaterialList));
    }
}
