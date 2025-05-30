package com.jps.tpms.api.dto;

import com.jps.tpms.api.domain.OrderFabricDo;
import lombok.Data;

import java.util.List;

/**
 * @className: OrderFabricDto
 * @author: zf
 * @date: 2025/4/19 11:22
 * @version: 1.0
 * @description:
 */
@Data
public class OrderFabricDto extends OrderFabricDo {

	private List<FabricColorDto> fabricColorDtos;

	private List<FabricSizeDto>  fabricSizeDtos;
}
