package com.jinpus.tpms.api.vo;

import com.jinpus.tpms.api.domain.CutOrderBedDo;
import com.jinpus.tpms.api.dto.CutOrderColorDto;
import lombok.Data;

import java.util.List;

/**
 * @className: CutOrderBedVo
 * @author: zf
 * @date: 2025/4/28 20:10
 * @version: 1.0
 * @description:
 */
@Data
public class CutOrderBedVo extends CutOrderBedDo {

	private List<CutOrderColorDto> cutOrderColorDto;
}
