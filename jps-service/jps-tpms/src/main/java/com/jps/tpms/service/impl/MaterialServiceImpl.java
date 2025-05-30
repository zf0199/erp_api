package com.jps.tpms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.tpms.api.domain.MaterialDo;
import com.jps.tpms.mapper.MaterialMapper;
import com.jps.tpms.service.MaterialService;
import org.springframework.stereotype.Service;

/**
 * 物料表
 *
 * @author pig
 * @date 2025-03-20 19:58:48
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, MaterialDo> implements MaterialService {

}
