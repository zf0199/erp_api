package com.jps.tpms.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jps.common.mybatis.base.BaseDo;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @className: WorkTicketDo
 * @author: zf
 * @date: 2025/5/19 14:19
 * @version: 1.0
 * @description:
 */
@Data
@TableName("work_ticket")
public class WorkTicketDo extends BaseDo {


	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 部门
	 */
	private String dept;

	private Long userId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 角色id
	 */
	private String roleId;

	/**
	 * 制单号
	 */
	private String wordOrderNo;

	/**
	 * 制单状态
	 */
	private String workerOrderStatus;

	/**
	 * 款号
	 */
	private String styleNo;

	/**
	 * 客户款号
	 */
	private String custStyleNo;

	/**
	 * 工序编号
	 */
	private String procedureNo;

	/**
	 * 工序名称
	 */
	private String procedureName;

	/**
	 * 工序类别名称
	 */
	private String procedureTypeName;

	/**
	 * 监测工序
	 */
	private String monitoringProcedure;

	/**
	 * 数量
	 */
	private String num;

	/**
	 * 工时
	 */
	private String manHour;

	/**
	 * 总工时
	 */
	private String sumManHour;

	/**
	 * 实际数量
	 */
	private String realNum;

	/**
	 * 回收日期
	 */
	private LocalDateTime recycleTime;

	/**
	 * 工价/每件
	 */
	private String price;

	/**
	 * 津贴
	 */
	private String subsidy;

	/**
	 * 津贴比例
	 */
	private String subsidyRatio;

	/**
	 * 系数
	 */
	private String coefficient;

	/**
	 * 工资
	 */
	private String salary;

	/**
	 * 津贴工资
	 */
	private String allowanceSalary;

	/**
	 * 款类id
	 */
	private Long styleId;

}
