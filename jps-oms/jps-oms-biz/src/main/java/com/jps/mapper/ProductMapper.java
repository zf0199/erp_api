package com.jps.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jps.domain.ProductDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @className: ProductService
 * @author: zf
 * @date: 2025/5/15 9:53
 * @version: 1.0
 * @description:
 */
@Mapper
public interface ProductMapper extends BaseMapper<ProductDo> {
}
