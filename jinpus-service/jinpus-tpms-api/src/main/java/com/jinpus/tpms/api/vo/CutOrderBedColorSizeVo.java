package com.jinpus.tpms.api.vo;

import com.jinpus.tpms.api.domain.CutOrderBedDo;
import com.jinpus.tpms.api.domain.CutOrderColorDo;
import com.jinpus.tpms.api.domain.CutOrderDo;
import lombok.Data;

/**
 * @className: CutOrderBedBundleVo
 * @author: zf
 * @date: 2025/4/29 16:35
 * @version: 1.0
 * @description:
 */
@Data
public class CutOrderBedColorSizeVo extends CutOrderColorDo {


	private  Long cutOrderId;
	private  Long cutColorId;

	private Long cutOrderBedId;

	private  String sizeName;

	private Integer sizeNum;

	private Integer orderSizeNum;

	private Integer sizeSumNum;

	private Integer bundleNum;

}
