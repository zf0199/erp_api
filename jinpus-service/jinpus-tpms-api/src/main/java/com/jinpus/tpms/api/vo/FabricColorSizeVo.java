package com.jinpus.tpms.api.vo;

import com.jinpus.tpms.api.dto.FabricColorDto;
import com.jinpus.tpms.api.dto.FabricSizeDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: FabricColorSizeVo
 * @author: zf
 * @date: 2025/4/22 17:47
 * @version: 1.0
 * @description:
 */
@Data
@NoArgsConstructor
public class FabricColorSizeVo {

	private Long orderFabricId;

	private List<FabricColorDto> fabricColorDtos;

	private List<FabricSizeDto>  fabricSizeDtos;
}
