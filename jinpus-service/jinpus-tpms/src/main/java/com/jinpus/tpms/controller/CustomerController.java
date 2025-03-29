package com.jinpus.tpms.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinpus.tpms.api.domain.CustomerDo;
import com.jinpus.tpms.service.CustomerService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.apache.commons.lang3.ObjectUtils;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 客户表
 *
 * @author pig
 * @date 2025-03-17 15:59:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer" )
@Tag(description = "customer" , name = "客户表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param customer 客户表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
//    @PreAuthorize("@pms.hasPermission('tpms_customer_view')" )
    public R getCustomerPage(@ParameterObject Page page, @ParameterObject CustomerDo customer) {
        LambdaQueryWrapper<CustomerDo> wrapper = Wrappers.lambdaQuery();
		Optional.ofNullable(customer)
				.filter(c-> ObjectUtils.isNotEmpty(customer.getName()))
				.ifPresent(t->
						wrapper.like(CustomerDo::getName,customer.getName())
								.or()
								.like(CustomerDo::getTel, customer.getName())
								.or()
								.like(CustomerDo::getStockAddr, customer.getName())
				);
		return R.ok(customerService.page(page, wrapper));
	}


    /**
     * 通过id查询客户表
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('tpms_customer_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(customerService.getById(id));
    }

    /**
     * 新增客户表
     * @param customer 客户表
     * @return R
     */
    @Operation(summary = "新增客户表" , description = "新增客户表" )
    @SysLog("新增客户表" )
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('tpms_customer_add')" )
    public R save(@RequestBody CustomerDo customer) {
        return R.ok(customerService.save(customer));
    }

    /**
     * 修改客户表
     * @param customer 客户表
     * @return R
     */
    @Operation(summary = "修改客户表" , description = "修改客户表" )
    @SysLog("修改客户表" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('tpms_customer_edit')" )
    public R updateById(@RequestBody CustomerDo customer) {
        return R.ok(customerService.updateById(customer));
    }

    /**
     * 通过id删除客户表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除客户表" , description = "通过id删除客户表" )
    @SysLog("通过id删除客户表" )
    @DeleteMapping
//    @PreAuthorize("@pms.hasPermission('tpms_customer_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(customerService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param customer 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
//    @PreAuthorize("@pms.hasPermission('tpms_customer_export')" )
    public List<CustomerDo> export(CustomerDo customer, Long[] ids) {
        return customerService.list(Wrappers.lambdaQuery(customer).in(ArrayUtil.isNotEmpty(ids), CustomerDo::getId, ids));
    }
}