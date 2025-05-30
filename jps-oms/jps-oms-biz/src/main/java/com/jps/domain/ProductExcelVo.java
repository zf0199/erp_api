package com.jps.domain;

import lombok.Data;

import java.util.List;

/**
 * @className: ProdctDo
 * @author: zf
 * @date: 2025/5/14 14:32
 * @version: 1.0
 * @description:
 */

@Data
public class ProductExcelVo extends ProductDo {

	private List<ChannelPriceDo>  channelPriceDos;
}
