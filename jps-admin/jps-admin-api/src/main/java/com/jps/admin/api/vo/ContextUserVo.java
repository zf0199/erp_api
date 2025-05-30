package com.jps.admin.api.vo;

import com.jps.admin.api.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: ContextUserVo
 * @author: zf
 * @date: 2025/5/27 13:39
 * @version: 1.0
 * @description:
 */
@Data
public class ContextUserVo extends SysUser implements Serializable {


	/**
	 *  部门名称
	 */
	private String deptName;

	/**
	 *  角色名称
	 */
	private String roleName;

	/**
	 * 权限标识集合
	 */
	@Schema(description = "权限标识集合")
	private String[] permissions;

	/**
	 * 角色集合
	 */
	@Schema(description = "角色标识集合")
	private Long[] roles;
}
