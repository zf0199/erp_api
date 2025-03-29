package com.jinpus.tpms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinpus.tpms.api.domain.CustomerDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper extends BaseMapper<CustomerDo> {


}