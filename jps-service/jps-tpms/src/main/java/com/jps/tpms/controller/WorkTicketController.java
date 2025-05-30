package com.jps.tpms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jps.tpms.api.domain.WorkTicketDo;
import com.jps.tpms.service.WorkTicketService;
import com.jps.common.core.util.R;
import com.jps.common.security.service.SecurityContextUser;
import com.jps.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @className: WorkTicketController
 * @author: zf
 * @date: 2025/5/19 15:43
 * @version: 1.0
 * @description:
 */

/**
 *   扫菲记录
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class WorkTicketController {



	private final WorkTicketService workTicketService;



	/**
	 * 工票扫菲分页查询
	 * @param page 分页对象
	 * @param workTicketDo  条件参数
	 * @return 分页数据
	 */
	@RequestMapping(value = "/workTicket/page" ,method = GET)
//	@PreAuthorize("@pms.hasPermission('tpms_sysCompany_view')" )
	public R getSysCompanyPage(@ParameterObject Page<WorkTicketDo> page, @ParameterObject WorkTicketDo workTicketDo ) {

		LambdaQueryWrapper<WorkTicketDo> wrapper = Wrappers.lambdaQuery();

		Optional.ofNullable(workTicketDo).ifPresent(t->
				wrapper.orderByDesc(WorkTicketDo::getCreateTime)
		);
		return R.ok(workTicketService.page(page, wrapper));
	}


	/**
	 * 新增扫菲记录
	 * @param workTicketDo 单位表
	 * @return R
	 */
	@RequestMapping(value = "/workTicket",method = POST)
//	@PreAuthorize("@pms.hasPermission('tpms_sysConpany_add')" )
	public R save(@RequestBody WorkTicketDo  workTicketDo) {
		workTicketDo.setRecycleTime(LocalDateTime.now());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info(">>> 当前登录用户是 {}",authentication.getName());
		SecurityContextUser user = SecurityUtils.getUser();
		workTicketService.saveWorkTicket(workTicketDo);
		return R.ok();
	}


	/**
	 *  获取制单工序工价
	 */
	@RequestMapping(value = "/workTicket/{workOrderId}",method = GET)
	R getProcedureDetails(@PathVariable("workOrderId") Long workOrderId){
		return R.ok(workTicketService.getProcedureDetails(workOrderId));
	}








}
