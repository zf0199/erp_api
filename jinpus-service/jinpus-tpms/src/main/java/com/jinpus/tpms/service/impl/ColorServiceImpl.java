package com.jinpus.tpms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinpus.tpms.api.domain.ColorDo;
import com.jinpus.tpms.mapper.ColorMapper;
import com.jinpus.tpms.service.ColorService;
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
