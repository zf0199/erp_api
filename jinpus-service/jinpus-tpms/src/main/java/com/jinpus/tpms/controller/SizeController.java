package com.jinpus.tpms.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.SizeDo;
import com.jinpus.tpms.service.SizeService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
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
 * 尺码表
 *
 * @author pig
 * @date 2025-03-20 20:01:03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/size" )
@Tag(description = "size" , name = "尺码表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SizeController {

    private final SizeService sizeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param size 尺码表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @HasPermission("basic_size_view")
    public R getSizePage(@ParameterObject Page page, @ParameterObject SizeDo size) {
        LambdaQueryWrapper<SizeDo> wrapper = Wrappers.lambdaQuery();
        return R.ok(sizeService.page(page, wrapper));
    }


    /**
     * 通过条件查询尺码表
     * @param size 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
    @HasPermission("basic_size_view")
    public R getDetails(@ParameterObject SizeDo size) {
        return R.ok(sizeService.list(Wrappers.query(size)));
    }

    /**
     * 新增尺码表
     * @param size 尺码表
     * @return R
     */
    @Operation(summary = "新增尺码表" , description = "新增尺码表" )
    @SysLog("新增尺码表" )
    @PostMapping
    @HasPermission("basic_size_add")
    public R save(@RequestBody SizeDo size) {
        return R.ok(sizeService.save(size));
    }

    /**
     * 修改尺码表
     * @param size 尺码表
     * @return R
     */
    @Operation(summary = "修改尺码表" , description = "修改尺码表" )
    @SysLog("修改尺码表" )
    @PutMapping
    @HasPermission("basic_size_edit")
    public R updateById(@RequestBody SizeDo size) {
        return R.ok(sizeService.updateById(size));
    }

    /**
     * 通过id删除尺码表
     * @param ids ${pk.attrName}列表
     * @return R
     */
    @Operation(summary = "通过id删除尺码表" , description = "通过id删除尺码表" )
    @SysLog("通过id删除尺码表" )
    @DeleteMapping
    @HasPermission("basic_size_del")
    public R removeById(@RequestBody String[] ids) {
        return R.ok(sizeService.removeBatchByIds(CollUtil.toList(ids)));
    }

//
//    /**
//     * 导出excel 表格
//     * @param size 查询条件
//   	 * @param ids 导出指定ID
//     * @return excel 文件流
//     */
//    @ResponseExcel
//    @GetMapping("/export")
//    @HasPermission("basic_size_export")
//    public List<Size> exportExcel(Size size,String[] ids) {
//        return sizeService.list(Wrappers.lambdaQuery(size).in(ArrayUtil.isNotEmpty(ids), Size::getN, ids));
//    }

    /**
     * 导入excel 表
     * @param sizeList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
    @HasPermission("basic_size_export")
    public R importExcel(@RequestExcel List<SizeDo> sizeList, BindingResult bindingResult) {
        return R.ok(sizeService.saveBatch(sizeList));
    }
}
