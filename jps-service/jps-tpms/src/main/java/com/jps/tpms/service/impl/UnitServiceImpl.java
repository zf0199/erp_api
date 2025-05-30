package com.jps.tpms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.tpms.api.domain.UnitDo;
import com.jps.tpms.mapper.UnitMapper;
import com.jps.tpms.service.UnitService;
import org.springframework.stereotype.Service;
/**
 * 单位表
 *
 * @author pig
 * @date 2025-03-17 16:19:20
 */
@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper, UnitDo> implements UnitService {
}