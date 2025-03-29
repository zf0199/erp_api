package com.jinpus.tpms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.UnitDo;
import com.jinpus.tpms.mapper.UnitMapper;
import com.jinpus.tpms.service.UnitService;
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