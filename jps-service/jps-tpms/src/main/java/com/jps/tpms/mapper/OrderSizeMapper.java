package com.jps.tpms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jps.tpms.api.domain.OrderSizeDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: OrderSizeMapper
 * @author: zf
 * @date: 2025/4/15 10:21
 * @version: 1.0
 * @description:
 */
@Mapper
public interface OrderSizeMapper extends BaseMapper<OrderSizeDo> {


	List<OrderSizeDo> selectOrderSizeDistinctSize( @Param("workOrderId") Long workOrderId);

}

