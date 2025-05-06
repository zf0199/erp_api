package com.jinpus.tpms.api.dto;

import com.jinpus.tpms.api.domain.CutOrderColorDo;
import com.jinpus.tpms.api.domain.CutOrderSizeDo;
import lombok.Data;

import java.util.List;

/**
 * @className: CutOrderColorDto
 * @author: zf
 * @date: 2025/4/28 15:48
 * @version: 1.0
 * @description:
 */
@Data
public class CutOrderColorDto  extends CutOrderColorDo {

	private List<CutOrderSizeDo> cutOrderSizeDos;
}
