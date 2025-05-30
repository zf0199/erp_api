package com.jps.tpms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jps.tpms.api.domain.OrderFabricDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderFabricMapper extends BaseMapper<OrderFabricDo> {


//	OrderFabricDo selectLatestFabricByOrderId();
}
