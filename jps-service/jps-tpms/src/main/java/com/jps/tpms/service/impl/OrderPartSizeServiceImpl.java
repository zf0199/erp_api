package com.jps.tpms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.tpms.api.domain.OrderPartSizeDo;
import com.jps.tpms.mapper.OrderPartSizeMapper;
import com.jps.tpms.service.OrderPartSizeService;
import org.springframework.stereotype.Service;

/**
 * @className: OrderPartSizeServiceImpl
 * @author: zf
 * @date: 2025/4/23 15:54
 * @version: 1.0
 * @description:
 */
@Service
public class OrderPartSizeServiceImpl extends ServiceImpl<OrderPartSizeMapper, OrderPartSizeDo> implements OrderPartSizeService {
}
