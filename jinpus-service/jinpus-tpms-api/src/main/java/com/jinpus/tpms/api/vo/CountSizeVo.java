package com.jinpus.tpms.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: CountSizeVo
 * @author: zf
 * @date: 2025/5/5 10:11
 * @version: 1.0
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountSizeVo {
	private String sizeName;

	private Integer orderNum;
	private Integer cutNum;

	private Integer  oweCut;
}
