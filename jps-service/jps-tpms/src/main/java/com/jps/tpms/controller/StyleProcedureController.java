package com.jps.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jps.tpms.api.domain.StyleProcedureDo;
import com.jps.tpms.service.StyleProcedureService;
import com.jps.common.core.util.R;
import com.jps.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.jps.common.security.annotation.HasPermission;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 款类默认工序表
 *
 * @author pig
 * @date 2025-03-27 15:31:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/styleProcedure" )
@Tag(description = "styleProcedure" , name = "款类默认工序表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class StyleProcedureController {

    private final StyleProcedureService styleProcedureService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param styleProcedure 款类默认工序表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @HasPermission("basic_styleProcedure_view")
    public R getStyleProcedurePage(@ParameterObject Page page, @ParameterObject StyleProcedureDo styleProcedure) {
        LambdaQueryWrapper<StyleProcedureDo> wrapper = Wrappers.lambdaQuery();
        return R.ok(styleProcedureService.page(page, wrapper));
    }


    /**
     * 通过条件查询款类默认工序表
     * @param styleProcedure 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
    @HasPermission("basic_styleProcedure_view")
    public R getDetails(@ParameterObject StyleProcedureDo styleProcedure) {
        return R.ok(styleProcedureService.list(Wrappers.query(styleProcedure)));
    }

    /**
     * 新增款类默认工序表
     * @param styleProcedure 款类默认工序表
     * @return R
     */
    @Operation(summary = "新增款类默认工序表" , description = "新增款类默认工序表" )
    @SysLog("新增款类默认工序表" )
    @PostMapping
    @HasPermission("basic_styleProcedure_add")
    public R save(@RequestBody StyleProcedureDo styleProcedure) {
        return R.ok(styleProcedureService.save(styleProcedure));
    }

    /**
     * 修改款类默认工序表
     * @param styleProcedure 款类默认工序表
     * @return R
     */
    @Operation(summary = "修改款类默认工序表" , description = "修改款类默认工序表" )
    @SysLog("修改款类默认工序表" )
    @PutMapping
    @HasPermission("basic_styleProcedure_edit")
    public R updateById(@RequestBody StyleProcedureDo styleProcedure) {
        return R.ok(styleProcedureService.updateById(styleProcedure));
    }

    /**
     * 通过id删除款类默认工序表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除款类默认工序表" , description = "通过id删除款类默认工序表" )
    @SysLog("通过id删除款类默认工序表" )
    @DeleteMapping
    @HasPermission("basic_styleProcedure_del")
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(styleProcedureService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param styleProcedure 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @HasPermission("basic_styleProcedure_export")
    public List<StyleProcedureDo> exportExcel(StyleProcedureDo styleProcedure, Long[] ids) {
        return styleProcedureService.list(Wrappers.lambdaQuery(styleProcedure).in(ArrayUtil.isNotEmpty(ids), StyleProcedureDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param styleProcedureList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
    @HasPermission("basic_styleProcedure_export")
    public R importExcel(@RequestExcel List<StyleProcedureDo> styleProcedureList, BindingResult bindingResult) {
        return R.ok(styleProcedureService.saveBatch(styleProcedureList));
    }
}
