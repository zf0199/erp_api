package com.jps.tpms.api.vo;

import lombok.Data;

import java.util.List;

/**
 * @className: CutOrderStatCountVo
 * @author: zf
 * @date: 2025/4/30 10:26
 * @version: 1.0
 * @description:
 */

@Data
public class CountCutOrderVo {

	private List<CountColorSizeVo>  countColorSizeVo;

	private List<CutOrderBedVo>  cutOrderBedVos;


	private List<CountColorVo> countColorVos;


}
