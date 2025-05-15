package com.jinpus.tpms.api.vo;

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
public class CountCutOrderColorVo {


	/**
	 * 颜色
	 */
	private String color;

	/**
	 *  整体进度
	 */
	private String overallProgress;

	/**
	 *   尺码
	 */
	private List<CountCutOrderColorSizeVo> countCutOrderColorSizeVo;



}
