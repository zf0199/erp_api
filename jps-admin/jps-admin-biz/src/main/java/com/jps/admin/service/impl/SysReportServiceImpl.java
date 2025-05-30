package com.jps.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jps.admin.api.vo.ReportTemplate;
import com.jps.admin.mapper.SysReportMapper;
import com.jps.admin.service.SysReportService;
import com.jps.admin.api.entity.SysReport;
import org.springframework.stereotype.Service;

/**
 * @className: SysReportServiceImpl
 * @author: zf
 * @date: 2025/5/16 18:00
 * @version: 1.0
 * @description:
 */
@Service
public class SysReportServiceImpl extends ServiceImpl<SysReportMapper, SysReport> implements SysReportService {

	@Override
	public Page getPage(Page<SysReport> page, SysReport sysReport) {



		if (ObjectUtils.isEmpty(sysReport.getMenuId())) {
			throw new RuntimeException("menuId is null");
		}
		LambdaQueryWrapper<SysReport> sysReportWrapper = Wrappers.lambdaQuery();
		sysReportWrapper.eq(SysReport::getMenuId, sysReport.getMenuId());
		Page<SysReport> sysReportPage = this.page(page, sysReportWrapper);
		return sysReportPage;
	}
}
