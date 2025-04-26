package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.OrderFabricDo;
import com.jinpus.tpms.service.OrderBomService;
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
 * 物料表
 *
 * @author pig
 * @date 2025-03-20 19:59:45
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/orderBom" )
@Tag(description = "orderBom" , name = "物料表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class OrderBomController {

    private final OrderBomService orderBomService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param orderBom 物料表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @HasPermission("basic_orderBom_view")
    public R getOrderBomPage(@ParameterObject Page page, @ParameterObject OrderFabricDo orderBom) {
        LambdaQueryWrapper<OrderFabricDo> wrapper = Wrappers.lambdaQuery();
        return R.ok(orderBomService.page(page, wrapper));
    }


    /**
     * 通过条件查询物料表
     * @param orderBom 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
    @HasPermission("basic_orderBom_view")
    public R getDetails(@ParameterObject OrderFabricDo orderBom) {
        return R.ok(orderBomService.list(Wrappers.query(orderBom)));
    }

    /**
     * 新增物料表
     * @param orderBom 物料表
     * @return R
     */
    @Operation(summary = "新增物料表" , description = "新增物料表" )
    @SysLog("新增物料表" )
    @PostMapping
    @HasPermission("basic_orderBom_add")
    public R save(@RequestBody OrderFabricDo orderBom) {
        return R.ok(orderBomService.save(orderBom));
    }

    /**
     * 修改物料表
     * @param orderBom 物料表
     * @return R
     */
    @Operation(summary = "修改物料表" , description = "修改物料表" )
    @SysLog("修改物料表" )
    @PutMapping
    @HasPermission("basic_orderBom_edit")
    public R updateById(@RequestBody OrderFabricDo orderBom) {
        return R.ok(orderBomService.updateById(orderBom));
    }

    /**
     * 通过id删除物料表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除物料表" , description = "通过id删除物料表" )
    @SysLog("通过id删除物料表" )
    @DeleteMapping
    @HasPermission("basic_orderBom_del")
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(orderBomService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param orderBom 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @HasPermission("basic_orderBom_export")
    public List<OrderFabricDo> exportExcel(OrderFabricDo orderBom, Long[] ids) {
        return orderBomService.list(Wrappers.lambdaQuery(orderBom).in(ArrayUtil.isNotEmpty(ids), OrderFabricDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param orderBomList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
    @HasPermission("basic_orderBom_export")
    public R importExcel(@RequestExcel List<OrderFabricDo> orderBomList, BindingResult bindingResult) {
        return R.ok(orderBomService.saveBatch(orderBomList));
    }
}
