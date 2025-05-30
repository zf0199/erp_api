package com.jps.tpms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jps.tpms.api.domain.OrderPartDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @className: OrderPartMapper
 * @author: zf
 * @date: 2025/4/23 15:48
 * @version: 1.0
 * @description:
 */

@Mapper
public interface OrderPartMapper extends BaseMapper<OrderPartDo> {
}
