package com.jinpus.tpms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinpus.tpms.api.domain.CutOrderBedDo;
import com.jinpus.tpms.api.dto.CutOrderBedDto;
import com.jinpus.tpms.api.vo.CutOrderBedColorSizeVo;
import com.jinpus.tpms.api.vo.CutOrderBedVo;

import java.util.List;

/**
 * @className: CutOrderBedService
 * @author: zf
 * @date: 2025/4/28 16:07
 * @version: 1.0
 * @description:
 */
public interface CutOrderBedService  extends IService<CutOrderBedDo> {


	 void saveCutOrderBed(CutOrderBedDto cutOrderBedDto);

	 void deleteCutOrderBed(Long id);


	 void updateCutOrderBed(CutOrderBedDto cutOrderBedDto);

	CutOrderBedVo getCutOrderBed (Long id);
	List<CutOrderBedColorSizeVo> getCutOrderBedBundle (Long id);

}
