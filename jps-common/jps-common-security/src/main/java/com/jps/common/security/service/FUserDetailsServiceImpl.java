

package com.jps.common.security.service;

import com.jps.admin.api.dto.UserDTO;
import com.jps.admin.api.feign.RemoteUserService;
import com.jps.admin.api.vo.ContextUserVo;
import com.jps.common.core.constant.CacheConstants;
import com.jps.common.core.util.R;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class FUserDetailsServiceImpl implements FUserDetailsService {

	private final RemoteUserService remoteUserService;

	private final CacheManager cacheManager;

	/**
	 * 用户名密码登录
	 * @param username 用户名
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(username) != null) {
			return (SecurityContextUser) cache.get(username).get();
		}

		// KP loadUserByUsername : 将用户信息存放在redis中
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username);
		R<ContextUserVo> result = remoteUserService.info(userDTO);
		// KP loadUserByUsername :加载用户信息 并且存在security上下文中
//		UserDetails userDetails = getUserDetails(result);
		SecurityContextUser userDetails = (SecurityContextUser) getUserDetails(result);
		if (cache != null) {
			cache.put(username, userDetails);
		}
		return userDetails;
	}
	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
