package com.jinpus.tpms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinpus.tpms.api.domain.CutOrderDo;
import com.jinpus.tpms.api.dto.CutOrderDto;
import com.jinpus.tpms.api.dto.CutOrderQueryDto;
import com.jinpus.tpms.api.vo.CountCutOrderVo;
import com.jinpus.tpms.api.vo.CutOrderVo;

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
