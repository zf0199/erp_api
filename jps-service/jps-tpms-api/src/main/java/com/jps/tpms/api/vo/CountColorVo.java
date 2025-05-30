package com.jps.tpms.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: CountColorVo
 * @author: zf
 * @date: 2025/5/5 10:11
 * @version: 1.0
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountColorVo {

	private String colorName;
	private List<CountSizeVo> countSizeVos;

}
