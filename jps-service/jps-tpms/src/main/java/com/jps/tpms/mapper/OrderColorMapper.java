package com.jps.tpms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jps.tpms.api.domain.OrderColorDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: OrderColorMapper
 * @author: zf
 * @date: 2025/4/15 15:17
 * @version: 1.0
 * @description:
 */
@Mapper
public interface OrderColorMapper  extends BaseMapper<OrderColorDo> {

	List<OrderColorDo> selectOrderColor(@Param("workOrderId") Long workOrderId);
}
