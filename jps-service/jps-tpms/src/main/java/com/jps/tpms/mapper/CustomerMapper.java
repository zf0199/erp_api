package com.jps.tpms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jps.tpms.api.domain.CustomerDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper extends BaseMapper<CustomerDo> {


}