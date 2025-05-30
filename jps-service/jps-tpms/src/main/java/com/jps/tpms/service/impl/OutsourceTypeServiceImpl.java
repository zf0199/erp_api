package com.jps.tpms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.tpms.api.domain.CustomerDo;
import com.jps.tpms.api.domain.OutsourceTypeDo;
import com.jps.tpms.api.vo.OutsourceTypeVo;
import com.jps.tpms.mapper.OutsourceTypeMapper;
import com.jps.tpms.service.CustomerService;
import com.jps.tpms.service.OutsourceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 外发类型
 *
 * @author pig
 * @date 2025-04-08 15:31:25
 */
@Service
@RequiredArgsConstructor
public class OutsourceTypeServiceImpl extends ServiceImpl<OutsourceTypeMapper, OutsourceTypeDo> implements OutsourceTypeService {

	private final  CustomerService customerService;

	@Override
	public Page getPage(Page page, OutsourceTypeDo outsourceType) {

		Page<OutsourceTypeDo> page1 = this.page(page);
		List<OutsourceTypeDo> records = page1.getRecords();
		records.stream().forEach(e->{
			Long customerId = e.getCustomerId();
			CustomerDo byId = customerService.getById(customerId);
			OutsourceTypeVo outsourceTypeVo = new OutsourceTypeVo();
			BeanUtils.copyProperties(e,outsourceTypeVo);
			outsourceTypeVo.setCustomerDo(byId);
		});
		return page1.setRecords(records);
	}

	@Override
	public OutsourceTypeVo selectOne(Long id) {

		OutsourceTypeDo byId = this.getById(id);
		LambdaQueryWrapper<CustomerDo> customerDoWrapper = Wrappers.lambdaQuery();
		CustomerDo byId1 = customerService.getById(byId.getCustomerId());

		OutsourceTypeVo outsourceTypeVo = new OutsourceTypeVo();

		BeanUtils.copyProperties(byId,outsourceTypeVo);
		outsourceTypeVo.setCustomerDo(byId1);
		return outsourceTypeVo;
	}


}
