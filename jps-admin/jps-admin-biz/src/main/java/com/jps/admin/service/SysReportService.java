package com.jps.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jps.admin.api.entity.SysReport;

/**
 * @className: SysReportService
 * @author: zf
 * @date: 2025/5/16 17:59
 * @version: 1.0
 * @description:
 */
public interface SysReportService extends IService<SysReport> {

	Page getPage( Page<SysReport> page,  SysReport sysReport);
}
