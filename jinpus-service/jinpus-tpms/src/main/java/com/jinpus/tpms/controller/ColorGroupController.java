package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.ColorGroupDo;
import com.jinpus.tpms.service.ColorGroupService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.lang3.ObjectUtils;
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
 * 颜色组
 *
 * @author pig
 * @date 2025-03-25 19:58:27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/colorGroup" )
@Tag(description = "colorGroup" , name = "颜色组管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class ColorGroupController {

    private final ColorGroupService colorGroupService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param colorGroup 颜色组
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
//    @HasPermission("basic_colorGroup_view")
    public R getColorGroupPage(@ParameterObject Page page, @ParameterObject ColorGroupDo colorGroup) {
        LambdaQueryWrapper<ColorGroupDo> wrapper = Wrappers.lambdaQuery();

		Optional.ofNullable(colorGroup).ifPresent(t->
				wrapper.like(ObjectUtils.isNotEmpty(t.getName()), ColorGroupDo::getName,t.getName())
						.or()
						.like(ObjectUtils.isNotEmpty(t.getName()), ColorGroupDo::getNo,t.getName())
		);
        return R.ok(colorGroupService.page(page, wrapper));
    }


    /**
     * 通过条件查询颜色组
     * @param colorGroup 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
//    @HasPermission("basic_colorGroup_view")
    public R getDetails(@ParameterObject ColorGroupDo colorGroup) {
        return R.ok(colorGroupService.list(Wrappers.query(colorGroup)));
    }

    /**
     * 新增颜色组
     * @param colorGroup 颜色组
     * @return R
     */
    @Operation(summary = "新增颜色组" , description = "新增颜色组" )
    @SysLog("新增颜色组" )
    @PostMapping
//    @HasPermission("basic_colorGroup_add")
    public R save(@RequestBody ColorGroupDo colorGroup) {
        return R.ok(colorGroupService.save(colorGroup));
    }

    /**
     * 修改颜色组
     * @param colorGroup 颜色组
     * @return R
     */
    @Operation(summary = "修改颜色组" , description = "修改颜色组" )
    @SysLog("修改颜色组" )
    @PutMapping
//    @HasPermission("basic_colorGroup_edit")
    public R updateById(@RequestBody ColorGroupDo colorGroup) {
        return R.ok(colorGroupService.updateById(colorGroup));
    }

    /**
     * 通过id删除颜色组
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除颜色组" , description = "通过id删除颜色组" )
    @SysLog("通过id删除颜色组" )
    @DeleteMapping
//    @HasPermission("basic_colorGroup_del")
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(colorGroupService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param colorGroup 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
//    @HasPermission("basic_colorGroup_export")
    public List<ColorGroupDo> exportExcel(ColorGroupDo colorGroup, Long[] ids) {
        return colorGroupService.list(Wrappers.lambdaQuery(colorGroup).in(ArrayUtil.isNotEmpty(ids), ColorGroupDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param colorGroupList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
//    @HasPermission("basic_colorGroup_export")
    public R importExcel(@RequestExcel List<ColorGroupDo> colorGroupList, BindingResult bindingResult) {
        return R.ok(colorGroupService.saveBatch(colorGroupList));
    }
}
