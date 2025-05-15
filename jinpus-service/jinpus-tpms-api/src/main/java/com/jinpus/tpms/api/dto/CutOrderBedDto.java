package com.jinpus.tpms.api.dto;
import com.jinpus.tpms.api.domain.CutOrderBedDo;
import lombok.Data;

import java.util.List;

/**
 * @className: CutOrderBundleDo
 * @author: zf
 * @date: 2025/4/28 14:05
 * @version: 1.0
 * @description:
 */


/**
 *   裁床分床表
 */
@Data
public class CutOrderBedDto extends CutOrderBedDo {


	private List<CutOrderColorDto> cutOrderColorDtos;



}
