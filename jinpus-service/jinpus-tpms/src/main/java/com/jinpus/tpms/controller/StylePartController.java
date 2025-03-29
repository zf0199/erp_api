package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.StylePartDo;
import com.jinpus.tpms.service.StylePartService;
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
 * 款类部位表
 *
 * @author pig
 * @date 2025-03-20 20:02:11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/stylePart" )
@Tag(description = "stylePart" , name = "款类部位表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class StylePartController {

    private final StylePartService stylePartService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param stylePart 款类部位表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @HasPermission("basic_stylePart_view")
    public R getStylePartPage(@ParameterObject Page page, @ParameterObject StylePartDo stylePart) {
        LambdaQueryWrapper<StylePartDo> wrapper = Wrappers.lambdaQuery();
        return R.ok(stylePartService.page(page, wrapper));
    }


    /**
     * 通过条件查询款类部位表
     * @param stylePart 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
    @HasPermission("basic_stylePart_view")
    public R getDetails(@ParameterObject StylePartDo stylePart) {
        return R.ok(stylePartService.list(Wrappers.query(stylePart)));
    }

    /**
     * 新增款类部位表
     * @param stylePart 款类部位表
     * @return R
     */
    @Operation(summary = "新增款类部位表" , description = "新增款类部位表" )
    @SysLog("新增款类部位表" )
    @PostMapping
    @HasPermission("basic_stylePart_add")
    public R save(@RequestBody StylePartDo stylePart) {
        return R.ok(stylePartService.save(stylePart));
    }

    /**
     * 修改款类部位表
     * @param stylePart 款类部位表
     * @return R
     */
    @Operation(summary = "修改款类部位表" , description = "修改款类部位表" )
    @SysLog("修改款类部位表" )
    @PutMapping
    @HasPermission("basic_stylePart_edit")
    public R updateById(@RequestBody StylePartDo stylePart) {
        return R.ok(stylePartService.updateById(stylePart));
    }

    /**
     * 通过id删除款类部位表
     * @param ids ${pk.attrName}列表
     * @return R
     */
    @Operation(summary = "通过id删除款类部位表" , description = "通过id删除款类部位表" )
    @SysLog("通过id删除款类部位表" )
    @DeleteMapping
    @HasPermission("basic_stylePart_del")
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(stylePartService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param stylePart 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @HasPermission("basic_stylePart_export")
    public List<StylePartDo> exportExcel(StylePartDo stylePart, Long[] ids) {
        return stylePartService.list(Wrappers.lambdaQuery(stylePart).in(ArrayUtil.isNotEmpty(ids), StylePartDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param stylePartList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
    @HasPermission("basic_stylePart_export")
    public R importExcel(@RequestExcel List<StylePartDo> stylePartList, BindingResult bindingResult) {
        return R.ok(stylePartService.saveBatch(stylePartList));
    }
}
