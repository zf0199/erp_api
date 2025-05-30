package com.jps.tpms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.tpms.api.domain.MaterialTypeDo;
import com.jps.tpms.mapper.MaterialTypeMapper;
import com.jps.tpms.service.MaterialTypeService;
import org.springframework.stereotype.Service;

/**
 * 物料类型表
 *
 * @author pig
 * @date 2025-03-20 19:59:10
 */
@Service
public class MaterialTypeServiceImpl extends ServiceImpl<MaterialTypeMapper, MaterialTypeDo> implements MaterialTypeService {

}
