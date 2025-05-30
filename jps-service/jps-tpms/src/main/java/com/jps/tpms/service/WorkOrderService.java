package com.jps.tpms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jps.tpms.api.domain.WorkOrderDo;
import com.jps.tpms.api.dto.WorkOrderDto;
import com.jps.tpms.api.vo.FabricColorSizeVo;
import com.jps.tpms.api.vo.WorkOrderInfoAppVo;
import com.jps.tpms.api.vo.WorkOrderVo;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @className: WorkOrderService
 * @author: zf
 * @date: 2025-03-20 20:02:51
 * @version: 1.0
 * @description: 生产制单基础表 业务层
 */

public interface WorkOrderService extends IService<WorkOrderDo> {


	Long add(WorkOrderDto workOrderDto);

	WorkOrderVo getList(String tag, WorkOrderDo workOrderDo);

	void updateById(String tag,WorkOrderDto workOrderDto);

	FabricColorSizeVo getColorAndSize(Long id);

	WorkOrderInfoAppVo getWorkOrderInfo(@PathVariable("id") Long id);

}
