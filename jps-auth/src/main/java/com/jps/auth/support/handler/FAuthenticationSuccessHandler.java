

package com.jps.auth.support.handler;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.jps.admin.api.entity.SysLog;
import com.jps.common.core.constant.CommonConstants;
import com.jps.common.core.constant.SecurityConstants;
import com.jps.common.core.util.SpringContextHolder;
import com.jps.common.log.event.SysLogEvent;
import com.jps.common.log.util.SysLogUtils;
import com.jps.common.security.component.PigCustomOAuth2AccessTokenResponseHttpMessageConverter;
import com.jps.common.security.service.SecurityContextUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * @className: JpsAuthenticationSuccessEventHandler
 * @author: zf
 * @date: 2025/5/14 16:03
 * @version: 1.0
 * @description:  用户登录成功处理器
 */

@Slf4j
public class FAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter = new PigCustomOAuth2AccessTokenResponseHttpMessageConverter();

	/**
	 * Called when a user has been successfully authenticated.
	 * @param request the request which caused the successful authentication
	 * @param response the response
	 * @param authentication the <tt>Authentication</tt> object which was created during
	 * the authentication process.
	 */
	@SneakyThrows
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;

		Map<String, Object> map = accessTokenAuthentication.getAdditionalParameters();
		if (MapUtil.isNotEmpty(map)) {
			// 发送异步日志事件
			SecurityContextUser userInfo = (SecurityContextUser) map.get(SecurityConstants.DETAILS_USER);
			log.info("用户：{} 登录成功", userInfo.getName());
			SecurityContextHolder.getContext().setAuthentication(accessTokenAuthentication);

			SysLog logVo = SysLogUtils.getSysLog();
			logVo.setTitle("登录成功");
			String startTimeStr = request.getHeader(CommonConstants.REQUEST_START_TIME);
			if (StrUtil.isNotBlank(startTimeStr)) {
				Long startTime = Long.parseLong(startTimeStr);
				Long endTime = System.currentTimeMillis();
				logVo.setTime(endTime - startTime);
			}
			logVo.setCreateBy(userInfo.getName());
			SpringContextHolder.publishEvent(new SysLogEvent(logVo));
		}

		// 输出token
		sendAccessTokenResponse(request, response, authentication);
	}

	private void sendAccessTokenResponse(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

		OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;

		OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
		OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
		Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

		OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
			.tokenType(accessToken.getTokenType())
			.scopes(accessToken.getScopes());
		if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
			builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
		}
		if (refreshToken != null) {
			builder.refreshToken(refreshToken.getTokenValue());
		}
		if (!CollectionUtils.isEmpty(additionalParameters)) {
			builder.additionalParameters(additionalParameters);
		}
		OAuth2AccessTokenResponse accessTokenResponse = builder.build();
		ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

		// 无状态 注意删除 context 上下文的信息
		SecurityContextHolder.clearContext();

		this.accessTokenHttpResponseConverter.write(accessTokenResponse, null, httpResponse);
	}

}
