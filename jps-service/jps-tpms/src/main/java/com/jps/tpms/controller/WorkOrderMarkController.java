package com.jps.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jps.tpms.api.domain.WorkOrderMarkDo;
import com.jps.tpms.service.WorkOrderMarkService;
import com.jps.common.core.util.R;
import com.jps.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.jps.common.security.annotation.HasPermission;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 制单部位表
 *
 * @author pig
 * @date 2025-03-20 20:03:14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/workOrderMark" )
@Tag(description = "workOrderMark" , name = "制单部位表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class WorkOrderMarkController {

    private final WorkOrderMarkService workOrderMarkService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param workOrderMark 制单部位表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @HasPermission("basic_workOrderMark_view")
    public R getWorkOrderMarkPage(@ParameterObject Page page, @ParameterObject WorkOrderMarkDo workOrderMark) {
        LambdaQueryWrapper<WorkOrderMarkDo> wrapper = Wrappers.lambdaQuery();
        return R.ok(workOrderMarkService.page(page, wrapper));
    }


    /**
     * 通过条件查询制单部位表
     * @param workOrderMark 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
    @HasPermission("basic_workOrderMark_view")
    public R getDetails(@ParameterObject WorkOrderMarkDo workOrderMark) {
        return R.ok(workOrderMarkService.list(Wrappers.query(workOrderMark)));
    }

    /**
     * 新增制单部位表
     * @param workOrderMark 制单部位表
     * @return R
     */
    @Operation(summary = "新增制单部位表" , description = "新增制单部位表" )
    @SysLog("新增制单部位表" )
    @PostMapping
    @HasPermission("basic_workOrderMark_add")
    public R save(@RequestBody WorkOrderMarkDo workOrderMark) {
        return R.ok(workOrderMarkService.save(workOrderMark));
    }

    /**
     * 修改制单部位表
     * @param workOrderMark 制单部位表
     * @return R
     */
    @Operation(summary = "修改制单部位表" , description = "修改制单部位表" )
    @SysLog("修改制单部位表" )
    @PutMapping
    @HasPermission("basic_workOrderMark_edit")
    public R updateById(@RequestBody WorkOrderMarkDo workOrderMark) {
        return R.ok(workOrderMarkService.updateById(workOrderMark));
    }

    /**
     * 通过id删除制单部位表
     * @param ids ${pk.attrName}列表
     * @return R
     */
    @Operation(summary = "通过id删除制单部位表" , description = "通过id删除制单部位表" )
    @SysLog("通过id删除制单部位表" )
    @DeleteMapping
    @HasPermission("basic_workOrderMark_del")
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(workOrderMarkService.removeBatchByIds(Arrays.asList(ids)));
    }


    /**
     * 导出excel 表格
     * @param workOrderMark 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @HasPermission("basic_workOrderMark_export")
    public List<WorkOrderMarkDo> exportExcel(WorkOrderMarkDo workOrderMark, Long[] ids) {
        return workOrderMarkService.list(Wrappers.lambdaQuery(workOrderMark).in(ArrayUtil.isNotEmpty(ids), WorkOrderMarkDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param workOrderMarkList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
    @HasPermission("basic_workOrderMark_export")
    public R importExcel(@RequestExcel List<WorkOrderMarkDo> workOrderMarkList, BindingResult bindingResult) {
        return R.ok(workOrderMarkService.saveBatch(workOrderMarkList));
    }
}
