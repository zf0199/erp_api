package com.jps.domain;

import lombok.Data;

import java.util.List;

/**
 * @className: SaleInfoDto
 * @author: zf
 * @date: 2025/5/17 12:02
 * @version: 1.0
 * @description:
 */
@Data
public class SaleInfoDto extends SaleInfoDo {


	private List<ChannelPriceDo> channelPriceDos;
}
