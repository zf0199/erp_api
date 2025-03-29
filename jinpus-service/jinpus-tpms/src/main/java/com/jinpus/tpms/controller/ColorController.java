package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.ColorDo;
import com.jinpus.tpms.service.ColorService;
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
 * 颜色表
 *
 * @author pig
 * @date 2025-03-26 11:39:37
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/color" )
@Tag(description = "color" , name = "颜色表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class ColorController {

    private final ColorService colorService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param color 颜色表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
//    @HasPermission("basic_color_view")
    public R getColorPage(@ParameterObject Page page, @ParameterObject ColorDo color) {
        LambdaQueryWrapper<ColorDo> wrapper = Wrappers.lambdaQuery();
		Optional.ofNullable(color).ifPresent(t->
				wrapper.like(ObjectUtils.isNotEmpty(t.getName()), ColorDo::getName,t.getName())
						.or()
						.like(ObjectUtils.isNotEmpty(t.getNo()), ColorDo::getNo,t.getName())
						.eq(ObjectUtils.isNotEmpty(t.getColorGroupId()), ColorDo::getColorGroupId,t.getColorGroupId())
						.orderByDesc(ColorDo::getCreateTime)
				);
        return R.ok(colorService.page(page, wrapper));
    }


    /**
     * 通过条件查询颜色表
     * @param color 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
//    @HasPermission("basic_color_view")
    public R getDetails(@ParameterObject ColorDo color) {
        return R.ok(colorService.list(Wrappers.query(color)));
    }

    /**
     * 新增颜色表
     * @param color 颜色表
     * @return R
     */
    @Operation(summary = "新增颜色表" , description = "新增颜色表" )
    @SysLog("新增颜色表" )
    @PostMapping
//    @HasPermission("basic_color_add")
    public R save(@RequestBody ColorDo color) {
        return R.ok(colorService.save(color));
    }

    /**
     * 修改颜色表
     * @param color 颜色表
     * @return R
     */
    @Operation(summary = "修改颜色表" , description = "修改颜色表" )
    @SysLog("修改颜色表" )
    @PutMapping
//    @HasPermission("basic_color_edit")
    public R updateById(@RequestBody ColorDo color) {
        return R.ok(colorService.updateById(color));
    }

    /**
     * 通过id删除颜色表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除颜色表" , description = "通过id删除颜色表" )
    @SysLog("通过id删除颜色表" )
    @DeleteMapping
//    @HasPermission("basic_color_del")
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(colorService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param color 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
//    @HasPermission("basic_color_export")
    public List<ColorDo> exportExcel(ColorDo color, Long[] ids) {
        return colorService.list(Wrappers.lambdaQuery(color).in(ArrayUtil.isNotEmpty(ids), ColorDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param colorList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
//    @HasPermission("basic_color_export")
    public R importExcel(@RequestExcel List<ColorDo> colorList, BindingResult bindingResult) {
        return R.ok(colorService.saveBatch(colorList));
    }
}
