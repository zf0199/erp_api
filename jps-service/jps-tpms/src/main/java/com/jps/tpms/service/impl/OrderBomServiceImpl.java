package com.jps.tpms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.tpms.api.domain.OrderFabricDo;
import com.jps.tpms.mapper.OrderFabricMapper;
import com.jps.tpms.service.OrderBomService;
import org.springframework.stereotype.Service;

/**
 * 物料表
 *
 * @author pig
 * @date 2025-03-20 19:59:45
 */
@Service
public class OrderBomServiceImpl extends ServiceImpl<OrderFabricMapper, OrderFabricDo> implements OrderBomService {

}
