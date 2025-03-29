package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.ProcedureDo;
import com.jinpus.tpms.service.ProcedureService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
 * 工序表
 *
 * @author pig
 * @date 2025-03-20 20:00:07
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/procedure" )
@Tag(description = "procedure" , name = "工序表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class ProcedureController {

    private final ProcedureService procedureService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param procedure 工序表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
//    @HasPermission("basic_procedure_view")
    public R getProcedurePage(@ParameterObject Page page, @ParameterObject ProcedureDo procedure) {
        LambdaQueryWrapper<ProcedureDo> wrapper = Wrappers.lambdaQuery();
		Optional.ofNullable(procedure)

				.ifPresent(t->
						wrapper.like(ObjectUtils.isNotEmpty(t.getName()), ProcedureDo::getName,t.getName())
								.or()
								.like(ObjectUtils.isNotEmpty(t.getName()), ProcedureDo::getNo,t.getName())
								.or()
								.orderByDesc(ProcedureDo::getCreateTime)
								.eq(ObjectUtils.isNotEmpty(t.getProcedureId()), ProcedureDo::getProcedureId,t.getProcedureId())
				);
        return R.ok(procedureService.page(page, wrapper));
    }


    /**
     * 通过条件查询工序表
     * @param procedure 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
//    @HasPermission("basic_procedure_view")
    public R getDetails(@ParameterObject ProcedureDo procedure) {
        return R.ok(procedureService.list(Wrappers.query(procedure)));
    }

    /**
     * 新增工序表
     * @param procedure 工序表
     * @return R
     */
    @Operation(summary = "新增工序表" , description = "新增工序表" )
    @SysLog("新增工序表" )
    @PostMapping
//    @HasPermission("basic_procedure_add")
    public R save(@RequestBody ProcedureDo procedure) {
        return R.ok(procedureService.save(procedure));
    }

    /**
     * 修改工序表
     * @param procedure 工序表
     * @return R
     */
    @Operation(summary = "修改工序表" , description = "修改工序表" )
    @SysLog("修改工序表" )
    @PutMapping
//    @HasPermission("basic_procedure_edit")
    public R updateById(@RequestBody ProcedureDo procedure) {
        return R.ok(procedureService.updateById(procedure));
    }

    /**
     * 通过id删除工序表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除工序表" , description = "通过id删除工序表" )
    @SysLog("通过id删除工序表" )
    @DeleteMapping
//    @HasPermission("basic_procedure_del")
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(procedureService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param procedure 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
//    @HasPermission("basic_procedure_export")
    public List<ProcedureDo> exportExcel(ProcedureDo procedure, Long[] ids) {
        return procedureService.list(Wrappers.lambdaQuery(procedure).in(ArrayUtil.isNotEmpty(ids), ProcedureDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param procedureList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
//    @HasPermission("basic_procedure_export")
    public R importExcel(@RequestExcel List<ProcedureDo> procedureList, BindingResult bindingResult) {
        return R.ok(procedureService.saveBatch(procedureList));
    }
}
