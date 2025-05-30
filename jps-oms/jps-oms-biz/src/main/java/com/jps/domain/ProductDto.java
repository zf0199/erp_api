package com.jps.domain;

import lombok.Data;

import java.util.List;

/**
 * @className: ProductDto
 * @author: zf
 * @date: 2025/5/17 11:27
 * @version: 1.0
 * @description:
 */
@Data
public class ProductDto extends ProductDo{

	private List<ChannelPriceDo> channelPriceDo;
}
