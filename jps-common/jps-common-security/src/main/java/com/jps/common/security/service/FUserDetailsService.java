package com.jps.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.jps.admin.api.vo.ContextUserVo;
import com.jps.common.core.constant.CommonConstants;
import com.jps.common.core.constant.SecurityConstants;
import com.jps.common.core.util.R;
import com.jps.common.core.util.RetOps;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lengleng
 * @date 2021/12/21
 */
public interface FUserDetailsService extends UserDetailsService, Ordered {

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	default boolean support(String clientId, String grantType) {
		return true;
	}

	/**
	 * 排序值 默认取最大的
	 * @return 排序值
	 */
	default int getOrder() {
		return 0;
	}

	/**
	 * 构建userdetails
	 * @param result 用户信息
	 * @return UserDetails
	 */
	default UserDetails getUserDetails(R<ContextUserVo> result) {
		ContextUserVo contextUserVo = RetOps.of(result).getData().orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

		Set<String> dbAuthsSet = new HashSet<>();

		if (ArrayUtil.isNotEmpty(contextUserVo.getRoles())) {
			// 获取角色
			Arrays.stream(contextUserVo.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
			// 获取资源
			dbAuthsSet.addAll(Arrays.asList(contextUserVo.getPermissions()));
		}

		Collection<GrantedAuthority> authorities = AuthorityUtils
			.createAuthorityList(dbAuthsSet.toArray(new String[0]));
//		SysUser user = info.getSysUser();

		// 构造 SecurityContext 安全上下文对象 SecurityContextUser
		return new SecurityContextUser(
				contextUserVo.getUserId(),
				contextUserVo.getDeptId(),
				contextUserVo.getUsername(),
				SecurityConstants.BCRYPT + contextUserVo.getPassword(),
				contextUserVo.getPhone(),
				contextUserVo.getDeptName(),
				true,
				true,
				true,
				StrUtil.equals(contextUserVo.getLockFlag(), CommonConstants.STATUS_NORMAL),
				authorities
		);
	}

	/**
	 * 通过用户实体查询
	 * @param pigUser user
	 * @return
	 */
	default UserDetails loadUserByUser(SecurityContextUser pigUser) {
		return this.loadUserByUsername(pigUser.getUsername());
	}

}
