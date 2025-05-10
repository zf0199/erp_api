package com.jinpus.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.ProcedureTypeDo;
import com.jinpus.tpms.service.ProcedureTypeService;
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

import java.util.*;

/**
 * 生产工序类别表
 *
 * @author pig
 * @date 2025-03-20 20:00:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/procedureType" )
@Tag(description = "procedureType" , name = "生产工序类别表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class ProcedureTypeController {

    private final ProcedureTypeService procedureTypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param procedureType 生产工序类别表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
//    @HasPermission("basic_procedureType_view")
    public R getProcedureTypePage(@ParameterObject Page page, @ParameterObject ProcedureTypeDo procedureType) {
        LambdaQueryWrapper<ProcedureTypeDo> wrapper = Wrappers.lambdaQuery();
		Optional.ofNullable(procedureType)
				.filter(e-> ObjectUtils.isNotEmpty(e.getName()))
				.ifPresent(t->
						wrapper.like(ProcedureTypeDo::getName,t.getName())
								.or()
								.like(ProcedureTypeDo::getName,t.getNo())
								.orderByDesc(ProcedureTypeDo::getCreateTime)
				);
        return R.ok(procedureTypeService.page(page, wrapper));
    }


    /**
     * 通过条件查询生产工序类别表
     * @param procedureType 查询条件
     * @return R  对象列表
     */
    @Operation(summary = "通过条件查询" , description = "通过条件查询对象" )
    @GetMapping("/details" )
//    @HasPermission("basic_procedureType_view")
    public R getDetails(@ParameterObject ProcedureTypeDo procedureType) {
        return R.ok(procedureTypeService.list(Wrappers.query(procedureType)));
    }

    /**
     * 新增生产工序类别表
     * @param procedureType 生产工序类别表
     * @return R
     */
    @Operation(summary = "新增生产工序类别表" , description = "新增生产工序类别表" )
    @SysLog("新增生产工序类别表" )
    @PostMapping
//    @HasPermission("basic_procedureType_add")
    public R save(@RequestBody ProcedureTypeDo procedureType) {
        return R.ok(procedureTypeService.save(procedureType));
    }

    /**
     * 修改生产工序类别表
     * @param procedureType 生产工序类别表
     * @return R
     */
    @Operation(summary = "修改生产工序类别表" , description = "修改生产工序类别表" )
    @SysLog("修改生产工序类别表" )
    @PutMapping
//    @HasPermission("basic_procedureType_edit")
    public R updateById(@RequestBody ProcedureTypeDo procedureType) {
        return R.ok(procedureTypeService.updateById(procedureType));
    }

    /**
     * 通过id删除生产工序类别表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除生产工序类别表" , description = "通过id删除生产工序类别表" )
    @SysLog("通过id删除生产工序类别表" )
    @DeleteMapping
//    @HasPermission("basic_procedureType_del")
    public R removeById(@RequestBody Long[] ids) {

		List<Long> list = Arrays.asList(1911623639888674818l, 1911623703780507650l, 1911623858135089153l);
		Set<Long> idSet = new HashSet<>(Arrays.asList(ids));
		boolean exists = list.stream().anyMatch(idSet::contains);
	if (!exists){
		return R.ok(procedureTypeService.removeBatchByIds(CollUtil.toList(ids)));
	}
      return R.failed("内置参数不允许删除");
    }


    /**
     * 导出excel 表格
     * @param procedureType 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
//    @HasPermission("basic_procedureType_export")
    public List<ProcedureTypeDo> exportExcel(ProcedureTypeDo procedureType, Long[] ids) {
        return procedureTypeService.list(Wrappers.lambdaQuery(procedureType).in(ArrayUtil.isNotEmpty(ids), ProcedureTypeDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param procedureTypeList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
//    @HasPermission("basic_procedureType_export")
    public R importExcel(@RequestExcel List<ProcedureTypeDo> procedureTypeList, BindingResult bindingResult) {
        return R.ok(procedureTypeService.saveBatch(procedureTypeList));
    }
}
