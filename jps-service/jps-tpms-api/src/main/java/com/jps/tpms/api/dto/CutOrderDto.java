package com.jps.tpms.api.dto;

import com.jps.tpms.api.domain.CutOrderDo;
import lombok.Data;

/**
 * @className: CutOrderDto
 * @author: zf
 * @date: 2025/4/28 14:42
 * @version: 1.0
 * @description:
 */
@Data
public class CutOrderDto  extends CutOrderDo {

	/**
	 *   床数
	 */
	private CutOrderBedDto cutOrderBedDto;


}
