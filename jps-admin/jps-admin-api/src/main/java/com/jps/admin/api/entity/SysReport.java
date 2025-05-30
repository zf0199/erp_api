package com.jps.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jps.common.mybatis.base.BaseDo;
import lombok.Data;

/**
 * @className: SysReport
 * @author: zf
 * @date: 2025/5/16 17:46
 * @version: 1.0
 * @description:
 */

@Data
@TableName("sys_report")
public class SysReport extends BaseDo {


	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 *   菜单id
	 */
	private Long menuId;


	/**
	 *  报表名称
	 */
	private String reportName;


	/**
	 *  报表内容
	 */
	private String reportContext;



}
