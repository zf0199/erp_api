package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.WorkOrderDo;
import com.jinpus.tpms.api.dto.WorkOrderDto;
import com.jinpus.tpms.service.WorkOrderService;
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
 * @className: StyleController
 * @author: zf
 * @date: 2025-03-20 20:02:51
 * @version: 1.0
 * @description: 生产制单基础表 控制层
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/workOrder" )
@Tag(description = "workOrder" , name = "生产制单基础表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param workOrder 生产制单基础表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
//    @HasPermission("basic_workOrder_view")
    public R getWorkOrderPage(@ParameterObject Page page, @ParameterObject WorkOrderDo workOrder) {
        LambdaQueryWrapper<WorkOrderDo> wrapper = Wrappers.lambdaQuery();
        return R.ok(workOrderService.page(page, wrapper));
    }


    /**
     * 通过条件查询生产制单基础表
     * @param workOrder 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
//    @HasPermission("basic_workOrder_view")
    public R getDetails(@ParameterObject WorkOrderDo workOrder) {
		return R.ok(workOrderService.getList(workOrder));
    }

    /**
     * 新增生产制单基础表
     * @param workOrder 生产制单基础表
     * @return R
     */
    @Operation(summary = "新增生产制单基础表" , description = "新增生产制单基础表" )
    @SysLog("新增生产制单基础表" )
    @PostMapping
//    @HasPermission("basic_workOrder_add")
    public R save(@RequestBody WorkOrderDto workOrder) {
		//新增制单

        return R.ok(workOrderService.add(workOrder));
    }

    /**
     * 修改生产制单基础表
     * @param workOrder 生产制单基础表
     * @return R
     */
    @Operation(summary = "修改生产制单基础表" , description = "修改生产制单基础表" )
    @SysLog("修改生产制单基础表" )
    @PutMapping
//    @HasPermission("basic_workOrder_edit")
    public R updateById(@RequestBody WorkOrderDto workOrder) {
		workOrderService.updateById(workOrder);
        return R.ok();
    }

    /**
     * 通过id删除生产制单基础表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除生产制单基础表" , description = "通过id删除生产制单基础表" )
    @SysLog("通过id删除生产制单基础表" )
    @DeleteMapping
//    @HasPermission("basic_workOrder_del")
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(workOrderService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param workOrder 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
//    @HasPermission("basic_workOrder_export")
    public List<WorkOrderDo> exportExcel(WorkOrderDo workOrder, Long[] ids) {
        return workOrderService.list(Wrappers.lambdaQuery(workOrder).in(ArrayUtil.isNotEmpty(ids), WorkOrderDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param workOrderList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
//    @HasPermission("basic_workOrder_export")
    public R importExcel(@RequestExcel List<WorkOrderDo> workOrderList, BindingResult bindingResult) {
        return R.ok(workOrderService.saveBatch(workOrderList));
    }
}
