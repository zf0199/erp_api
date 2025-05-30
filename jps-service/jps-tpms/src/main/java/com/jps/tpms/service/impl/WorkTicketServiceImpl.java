package com.jps.tpms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.tpms.api.domain.OrderProcedureDetailDo;
import com.jps.tpms.api.domain.WorkOrderDo;
import com.jps.tpms.api.domain.WorkTicketDo;
import com.jps.tpms.mapper.OrderProcedureDetailMapper;
import com.jps.tpms.mapper.WorkOrderMapper;
import com.jps.tpms.mapper.WorkTicketMapper;
import com.jps.tpms.service.WorkTicketService;
import com.jps.common.security.service.SecurityContextUser;
import com.jps.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @className: WorkTicketServiceImpl
 * @author: zf
 * @date: 2025/5/19 15:42
 * @version: 1.0
 * @description:
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkTicketServiceImpl extends ServiceImpl<WorkTicketMapper, WorkTicketDo> implements WorkTicketService {

	private final  WorkOrderMapper workOrderMapper;

	private final OrderProcedureDetailMapper orderProcedureDetailMapper;



	@Override
	public List<OrderProcedureDetailDo> getProcedureDetails(Long workOrderId) {

		LambdaQueryWrapper<OrderProcedureDetailDo> orderProcedureDetailWrapper = Wrappers.lambdaQuery();
		orderProcedureDetailWrapper.eq(OrderProcedureDetailDo::getWorkOrderId,workOrderId);
		return orderProcedureDetailMapper.selectList(orderProcedureDetailWrapper);
	}




	@Override
	public void saveWorkTicket(WorkTicketDo workTicketDo) {

		// 设置回收时间
		workTicketDo.setRecycleTime(LocalDateTime.now());
		//  获取当前用户信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info(">>> 当前登录用户是 {}",authentication.getName());

		SecurityContextUser user = SecurityUtils.getUser();
		// 用户名
		workTicketDo.setUsername(user.getUsername());
		// 部门名称
		workTicketDo.setDept(user.getDeptName());
		// 姓名
		workTicketDo.setName(user.getName());


		String wordOrderNo = workTicketDo.getWordOrderNo();

		LambdaQueryWrapper<WorkOrderDo> workOrderDoWrapper = Wrappers.lambdaQuery();
		workOrderDoWrapper.eq(WorkOrderDo::getOrderNo,wordOrderNo);

		WorkOrderDo workOrderDo = workOrderMapper.selectOne(workOrderDoWrapper);

		Integer orderStatus = workOrderDo.getOrderStatus();
		workTicketDo.setWorkerOrderStatus(String.valueOf(orderStatus));

	}
}
