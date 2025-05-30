package com.jps.tpms.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jps.tpms.api.domain.OutsourceTypeDo;
import com.jps.tpms.api.vo.OutsourceTypeVo;

public interface OutsourceTypeService extends IService<OutsourceTypeDo> {

	Page getPage( Page page,  OutsourceTypeDo outsourceType);

	OutsourceTypeVo selectOne(Long id);


}
