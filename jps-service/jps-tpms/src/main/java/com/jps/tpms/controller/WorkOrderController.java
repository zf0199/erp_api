package com.jps.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jps.tpms.api.domain.WorkOrderDo;
import com.jps.tpms.api.dto.WorkOrderDto;
import com.jps.tpms.service.WorkOrderService;
import com.jps.common.core.util.R;
import com.jps.common.log.annotation.SysLog;
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

import static org.springframework.web.bind.annotation.RequestMethod.*;


/**
 * @className: StyleController
 * @author: zf
 * @date: 2025-03-20 20:02:51
 * @version: 1.0
 * @description: 生产制单基础表 控制层
 */
@RestController
@RequiredArgsConstructor
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
	@RequestMapping(value = "/workOrder/page",method = GET)
//    @HasPermission("basic_workOrder_view")
    public R getWorkOrderPage(@ParameterObject Page page, @ParameterObject WorkOrderDo workOrder) {
        LambdaQueryWrapper<WorkOrderDo> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(ObjectUtils.isNotEmpty(workOrder.getStyleId()),WorkOrderDo::getStyleId,workOrder.getStyleId())
				.or()
				.like(ObjectUtils.isNotEmpty(workOrder.getOrderNo()),WorkOrderDo::getOrderNo,workOrder.getOrderNo())
				.or()
				.eq(ObjectUtils.isNotEmpty(workOrder.getCustomerId()),WorkOrderDo::getCustomerId,workOrder.getCustomerId())
				.or()
				.eq(ObjectUtils.isNotEmpty(workOrder.getStyleNo()),WorkOrderDo::getStyleNo,workOrder.getStyleNo())
		.orderByDesc(WorkOrderDo::getCreateTime);
        return R.ok(workOrderService.page(page, wrapper));
    }


    /**
     * 通过条件查询生产制单基础表
     * @param workOrder 查询条件
     * @param tag 标签区分 colorAndSize fabric
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
	@RequestMapping(value = "/workOrder/details",method = GET)
//    @HasPermission("basic_workOrder_view")
    public R getDetails(@RequestParam(value = "tag") String tag,@ParameterObject WorkOrderDo workOrder) {
		return R.ok(workOrderService.getList(tag,workOrder));
    }

    /**
     * 新增生产制单基础表
     * @param workOrder 生产制单基础表
     * @return R
     */
    @Operation(summary = "新增生产制单基础表" , description = "新增生产制单基础表" )
    @SysLog("新增生产制单基础表" )
	@RequestMapping(value = "/workOrder",method = POST)
//    @HasPermission("basic_workOrder_add")
    public R save(@RequestBody WorkOrderDto workOrder) {
		//新增制单

        return R.ok(workOrderService.add(workOrder));
    }

    /**
     * 修改生产制单基础表
     * @param workOrder 生产制单基础表
	 * @param tag 标签区分 colorAndSize fabric
     * @return R
     */
    @Operation(summary = "修改生产制单基础表" , description = "修改生产制单基础表" )
    @SysLog("修改生产制单基础表" )
	@RequestMapping(value = "/workOrder",method = PUT)
//    @HasPermission("basic_workOrder_edit")
    public R updateById(@RequestParam(value = "tag") String tag,@RequestBody WorkOrderDto workOrder) {
		workOrderService.updateById(tag,workOrder);
        return R.ok();
    }

    /**
     * 通过id删除生产制单基础表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除生产制单基础表" , description = "通过id删除生产制单基础表" )
    @SysLog("通过id删除生产制单基础表" )
	@RequestMapping(value = "/workOrder",method = DELETE)
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
	@RequestMapping(value = "/workOrder/export",method = GET)
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
	@RequestMapping(value = "/workOrder/import",method = POST)
//    @HasPermission("basic_workOrder_export")
    public R importExcel(@RequestExcel List<WorkOrderDo> workOrderList, BindingResult bindingResult) {
        return R.ok(workOrderService.saveBatch(workOrderList));
    }


	/**
	 *   查询 颜色尺码
	 * @param id 制单id
	 * @return
	 */


	@RequestMapping(value = "/workOrder/colorAndSize",method = GET)
	public R colorAndSize(Long id){
	return R.ok(workOrderService.getColorAndSize(id))	;
	}


	/**
	 *   手动录入票飞 根据制单id获取详细信息
	 * @param id 制单id
	 * @return .
	 */
	@RequestMapping(value = "/workOrder/getWorkInfo/{id}",method = GET)
	public R getWorkOrderInfo(@PathVariable("id") Long id){

		return R.ok(workOrderService.getWorkOrderInfo(id));

	}
}
