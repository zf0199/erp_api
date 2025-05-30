package com.jps.auth.support.filter;

import cn.hutool.core.util.StrUtil;
import com.jps.common.core.constant.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @className: LoginOriginFilter
 * @author: zf
 * @date: 2025/5/15 18:02
 * @version: 1.0
 * @description: 登录员过滤器
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginOriginFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

		// 不是登录请求，直接向下执行
		if (!StrUtil.containsAnyIgnoreCase(request.getRequestURI(), SecurityConstants.OAUTH_TOKEN_URL)) {
			chain.doFilter(request, response);
		}
		chain.doFilter(request, response);
	}
}
