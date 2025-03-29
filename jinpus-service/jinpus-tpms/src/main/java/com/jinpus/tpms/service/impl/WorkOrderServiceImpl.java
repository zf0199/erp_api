package com.jinpus.tpms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.WorkOrderDo;
import com.jinpus.tpms.mapper.WorkOrderMapper;
import com.jinpus.tpms.service.WorkOrderService;
import org.springframework.stereotype.Service;

/**
 * 生产制单基础表
 *
 * @author pig
 * @date 2025-03-20 20:02:51
 */
@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrderDo> implements WorkOrderService {

}
