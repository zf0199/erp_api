package com.jps.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jps.common.mybatis.base.BaseDo;
import lombok.Data;

/**
 * @className: ChannelDo
 * @author: zf
 * @date: 2025/5/17 11:25
 * @version: 1.0
 * @description:
 */
@Data
@TableName("channel_price")
public class ChannelPriceDo extends BaseDo {


	/**
	 *   id
	 */

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;


	/**
	 *  商品id
	 */
	private Long productId;


	/**
	 *   销售信息id
	 */
	private Long saleInfoId;

	/**
	 *   渠道名称
	 */
	private String  channelName;

	/**
	 *  渠道价格
	 */
	private String  channelPrice;



}
