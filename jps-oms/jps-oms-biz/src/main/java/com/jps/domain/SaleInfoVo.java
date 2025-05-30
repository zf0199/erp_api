package com.jps.domain;

import lombok.Data;

import java.util.List;

/**
 * @className: SaleInfoVo
 * @author: zf
 * @date: 2025/5/17 11:53
 * @version: 1.0
 * @description:
 */
@Data
public class SaleInfoVo extends SaleInfoDo{

	private List<ChannelPriceDo> channelPriceDo;
}
