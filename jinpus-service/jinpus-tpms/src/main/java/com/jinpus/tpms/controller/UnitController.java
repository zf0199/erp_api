package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.UnitDo;
import com.jinpus.tpms.service.UnitService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * 单位表
 *
 * @author pig
 * @date 2025-03-17 16:19:20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/unit" )
@Tag(description = "unit" , name = "单位表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class UnitController {

    private final UnitService unitService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param unit 单位表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
//    @PreAuthorize("@pms.hasPermission('tpms_unit_view')" )
    public R getUnitPage(@ParameterObject Page page, @ParameterObject UnitDo unit) {
   LambdaQueryWrapper<UnitDo> wrapper = Wrappers.lambdaQuery();
		Optional.ofNullable(unit).ifPresent(t->
				wrapper.like(ObjectUtils.isNotEmpty(t.getUnitName()),UnitDo::getUnitName,t.getUnitName())
						.orderByDesc(UnitDo::getCreateTime)
				);
        return R.ok(unitService.page(page, wrapper));
    }


    /**
     * 通过id查询单位表
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('tpms_unit_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(unitService.getById(id));
    }

    /**
     * 新增单位表
     * @param unit 单位表
     * @return R
     */
    @Operation(summary = "新增单位表" , description = "新增单位表" )
    @SysLog("新增单位表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('unit_all_add')" )
    public R save(@RequestBody UnitDo unit) {
        return R.ok(unitService.save(unit));
    }

    /**
     * 修改单位表
     * @param unit 单位表
     * @return R
     */
    @Operation(summary = "修改单位表" , description = "修改单位表" )
    @SysLog("修改单位表" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('tpms_unit_edit')" )
    public R updateById(@RequestBody UnitDo unit) {
        return R.ok(unitService.updateById(unit));
    }

    /**
     * 通过id删除单位表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除单位表" , description = "通过id删除单位表" )
    @SysLog("通过id删除单位表" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('unit_delete')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(unitService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param unit 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
//    @PreAuthorize("@pms.hasPermission('tpms_unit_export')" )
    public List<UnitDo> export(UnitDo unit, Long[] ids) {
        return unitService.list(Wrappers.lambdaQuery(unit).in(ArrayUtil.isNotEmpty(ids), UnitDo::getId, ids));
    }
}