package com.jps.tpms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jps.tpms.api.domain.CutOrderDo;
import com.jps.tpms.api.dto.CutOrderDto;
import com.jps.tpms.api.dto.CutOrderQueryDto;
import com.jps.tpms.api.vo.CountCutOrderVo;
import com.jps.tpms.api.vo.CutOrderVo;

/**
 * @className: CutOrderService
 * @author: zf
 * @date: 2025/4/28 9:23
 * @version: 1.0
 * @description:
 */
public interface CutOrderService  extends IService<CutOrderDo> {


	Page getPage(Page page, CutOrderQueryDto cutOrderDo);



	void updateCutOrder(CutOrderDto cutOrderDto);

	CutOrderVo getCutOrderDetail(Long cutOrderId);

	CountCutOrderVo getCutOrderStatCount(Long id);

	Integer getBundleNum(Long cutOrderId);

}
