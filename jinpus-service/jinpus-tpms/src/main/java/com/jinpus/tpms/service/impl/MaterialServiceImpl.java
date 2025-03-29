package com.jinpus.tpms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.MaterialDo;
import com.jinpus.tpms.mapper.MaterialMapper;
import com.jinpus.tpms.service.MaterialService;
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
