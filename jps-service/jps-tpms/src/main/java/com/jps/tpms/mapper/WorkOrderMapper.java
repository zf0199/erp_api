package com.jps.tpms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jps.tpms.api.domain.WorkOrderDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkOrderMapper extends BaseMapper<WorkOrderDo> {

}
