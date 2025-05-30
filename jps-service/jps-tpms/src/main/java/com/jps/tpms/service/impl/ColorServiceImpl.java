package com.jps.tpms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.tpms.api.domain.ColorDo;
import com.jps.tpms.mapper.ColorMapper;
import com.jps.tpms.service.ColorService;
import org.springframework.stereotype.Service;

/**
 * 颜色表
 *
 * @author pig
 * @date 2025-03-26 11:39:37
 */
@Service
public class ColorServiceImpl extends ServiceImpl<ColorMapper, ColorDo> implements ColorService {

}
