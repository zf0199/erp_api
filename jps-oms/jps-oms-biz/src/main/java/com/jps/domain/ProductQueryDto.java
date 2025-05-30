package com.jps.domain;

import lombok.Data;

/**
 * @className: ProdctDo
 * @author: zf
 * @date: 2025/5/14 14:32
 * @version: 1.0
 * @description:
 */

@Data
public class ProductQueryDto extends ProductDo {

	private String startTime;

	private String endTime;
}
