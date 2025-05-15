package com.jinpus.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @className: SysCompanyDo
 * @author: zf
 * @date: 2025/5/14 15:56
 * @version: 1.0
 * @description:
 */
@TableName("sys_company")
@Data
public class SysCompanyDo {


	/**
	 *  id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;


	/**
	 *   公司名称
	 */
	private String companyName;

	/**
	 *   公司编号
	 */
	private String companyNo;

	/**
	 *   服务器地址-test
	 */
	private String serverAddressTest;

	/***
	 *   服务器地址-pro
	 */
	private String serverAddressPro;


}
