package com.jps.tpms.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jps.tpms.api.domain.OutsourceTypeDo;
import com.jps.tpms.service.OutsourceTypeService;
import com.jps.common.core.util.R;
import com.jps.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 外发类型
 *
 * @author pig
 * @date 2025-04-08 15:31:25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/outsourceType" )
@Tag(description = "outsourceType" , name = "外发类型管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class OutsourceTypeController {

    private final OutsourceTypeService outsourceTypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param outsourceType 外发类型
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
//    @HasPermission("basic_outsourceType_view")
    public R getOutsourceTypePage(Page page,  OutsourceTypeDo outsourceType) {
//		LambdaQueryWrapper<OutsourceTypeDo> wrapper = Wrappers.lambdaQuery();
//		Optional.ofNullable(outsourceType).ifPresent(e->
//			wrapper.like(ObjectUtils.isNotEmpty(e.getTypeName()),OutsourceTypeDo::getTypeName,e.getTypeName())
//					.or()
//					.like(ObjectUtils.isNotEmpty(e.getTypeName()),OutsourceTypeDo::getNo,e.getTypeName())
//					.orderByDesc(OutsourceTypeDo::getCreateTime)
//		);
//        return R.ok(outsourceTypeService.page(page, wrapper));

		return R.ok(outsourceTypeService.getPage(page,outsourceType));

    }


    /**
     * 通过id查询外发类型
     * @param   id 外发id
     * @return R  对象列表
     */
    @GetMapping("/details/{id}" )
    public R getDetails(@PathVariable Long id) {
        return R.ok(outsourceTypeService.selectOne(id));
    }

    /**
     * 新增外发类型
     * @param outsourceType 外发类型
     * @return R
     */
    @Operation(summary = "新增外发类型" , description = "新增外发类型" )
    @SysLog("新增外发类型" )
    @PostMapping
    public R save(@RequestBody OutsourceTypeDo outsourceType) {
        return R.ok(outsourceTypeService.save(outsourceType));
    }

    /**
     * 修改外发类型
     * @param outsourceType 外发类型
     * @return R
     */
    @Operation(summary = "修改外发类型" , description = "修改外发类型" )
    @SysLog("修改外发类型" )
    @PutMapping
    public R updateById(@RequestBody OutsourceTypeDo outsourceType) {
        return R.ok(outsourceTypeService.updateById(outsourceType));
    }

    /**
     * 通过id删除外发类型
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除外发类型" , description = "通过id删除外发类型" )
    @SysLog("通过id删除外发类型" )
    @DeleteMapping
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(outsourceTypeService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param outsourceType 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
//    @HasPermission("basic_outsourceType_export")
    public List<OutsourceTypeDo> exportExcel(OutsourceTypeDo outsourceType, Long[] ids) {
        return outsourceTypeService.list(Wrappers.lambdaQuery(outsourceType).in(ArrayUtil.isNotEmpty(ids), OutsourceTypeDo::getId, ids));
    }

    /**
     * 导入excel 表
     * @param outsourceTypeList 对象实体列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
//    @HasPermission("basic_outsourceType_export")
    public R importExcel(@RequestExcel List<OutsourceTypeDo> outsourceTypeList, BindingResult bindingResult) {
        return R.ok(outsourceTypeService.saveBatch(outsourceTypeList));
    }
}
