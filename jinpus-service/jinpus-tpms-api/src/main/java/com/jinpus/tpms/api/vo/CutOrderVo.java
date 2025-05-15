package com.jinpus.tpms.api.vo;

import com.jinpus.tpms.api.domain.CutOrderBedDo;
import com.jinpus.tpms.api.domain.CutOrderDo;
import com.jinpus.tpms.api.domain.WorkOrderDo;
import lombok.Data;

import java.util.List;

/**
 * @className: CutOrderVo
 * @author: zf
 * @date: 2025/4/26 12:01
 * @version: 1.0
 * @description:
 */
@Data
public class CutOrderVo extends CutOrderDo {


	private WorkOrderDo workOrderDo;

	private List<CutOrderBedDo> cutOrderBedDo;
}
