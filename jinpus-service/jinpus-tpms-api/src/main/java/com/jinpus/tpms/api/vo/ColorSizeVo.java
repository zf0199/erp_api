package com.jinpus.tpms.api.vo;

import com.jinpus.tpms.api.domain.OrderColorDo;
import com.jinpus.tpms.api.domain.OrderSizeDo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: ColorSizeVo
 * @author: zf
 * @date: 2025/4/18 14:14
 * @version: 1.0
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorSizeVo {

	private Long workOrderId;

	private List<OrderColorDo> OrderColors;

	private List<OrderSizeDo> OrderSizes;


}
