package com.jinpus.tpms.api.dto;

import com.jinpus.tpms.api.domain.CutOrderDo;
import lombok.Data;

/**
 * @className: CutOrderQueryDto
 * @author: zf
 * @date: 2025/5/6 15:13
 * @version: 1.0
 * @description:
 */
@Data
public class CutOrderQueryDto  extends CutOrderDo {



	private String orderNo;

	private Long styleId;

	private String styleNo;

	private String custStyleNo;

	private Integer orderStatus;

	private Long customerId;


}
