package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.MakeMethodDo;
import com.jinpus.tpms.service.MakeMethodService;
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
 * 制作方法
 *
 * @author zf
 * @date 2025-03-20 19:58:04
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/makeMethod" )
@Tag(description = "makeMethod" , name = "制作方法管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MakeMethodController {

    private final MakeMethodService makeMethodService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param makeMethod 制作方法
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @HasPermission("basic_makeMethod_view")
    public R getMakeMethodPage(@ParameterObject Page page, @ParameterObject MakeMethodDo makeMethod) {
        LambdaQueryWrapper<MakeMethodDo> wrapper = Wrappers.lambdaQuery();
        return R.ok(makeMethodService.page(page, wrapper));
    }


    /**
     * 通过条件查询制作方法
     * @param makeMethod 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
    @HasPermission("basic_makeMethod_view")
    public R getDetails(@ParameterObject MakeMethodDo makeMethod) {
        return R.ok(makeMethodService.list(Wrappers.query(makeMethod)));
    }

    /**
     * 新增制作方法
     * @param makeMethod 制作方法
     * @return R
     */
    @Operation(summary = "新增制作方法" , description = "新增制作方法" )
    @SysLog("新增制作方法" )
    @PostMapping
    @HasPermission("basic_makeMethod_add")
    public R save(@RequestBody MakeMethodDo makeMethod) {
        return R.ok(makeMethodService.save(makeMethod));
    }

    /**
     * 修改制作方法
     * @param makeMethod 制作方法
     * @return R
     */
    @Operation(summary = "修改制作方法" , description = "修改制作方法" )
    @SysLog("修改制作方法" )
    @PutMapping
    @HasPermission("basic_makeMethod_edit")
    public R updateById(@RequestBody MakeMethodDo makeMethod) {
        return R.ok(makeMethodService.updateById(makeMethod));
    }

    /**
     * 通过id删除制作方法
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除制作方法" , description = "通过id删除制作方法" )
    @SysLog("通过id删除制作方法" )
    @DeleteMapping
    @HasPermission("basic_makeMethod_del")
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(makeMethodService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param makeMethod 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @HasPermission("basic_makeMethod_export")
    public List<MakeMethodDo> exportExcel(MakeMethodDo makeMethod, Long[] ids) {
        return makeMethodService.list(Wrappers.lambdaQuery(makeMethod).in(ArrayUtil.isNotEmpty(ids), MakeMethodDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param makeMethodList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
    @HasPermission("basic_makeMethod_export")
    public R importExcel(@RequestExcel List<MakeMethodDo> makeMethodList, BindingResult bindingResult) {
        return R.ok(makeMethodService.saveBatch(makeMethodList));
    }
}
