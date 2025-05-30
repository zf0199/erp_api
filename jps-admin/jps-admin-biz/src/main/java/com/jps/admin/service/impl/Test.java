package com.jps.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.jps.admin.api.vo.ReportTemplate;

import java.util.Date;

/**
 * @className: Test
 * @author: zf
 * @date: 2025/5/30 11:10
 * @version: 1.0
 * @description:
 */
public class Test {
	public static void main(String[] args) {
		ReportTemplate reportTemplate =ReportTemplate.builder().build();
		System.out.println(reportTemplate);
		String json = JSON.toJSONString(reportTemplate, true);
		System.out.println(json);
		System.out.println(ReportTemplate.generateGuid());
		System.out.println(ReportTemplate.generateDotNetDateFormat(new Date()));
	}
}
