package com.jps.common.security.service;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.jps.admin.api.entity.SysOauthClientDetails;
import com.jps.admin.api.feign.RemoteClientDetailsService;
import com.jps.common.core.constant.CacheConstants;
import com.jps.common.core.constant.SecurityConstants;
import com.jps.common.core.util.RetOps;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

/**
 * 查询客户端相关信息实现
 *
 * @author lengleng
 * @date 2022/5/29
 */
@RequiredArgsConstructor
public class PigRemoteRegisteredClientRepository implements RegisteredClientRepository {

	/**
	 * 刷新令牌有效期默认 30 天
	 */
	private final static int refreshTokenValiditySeconds = 60 * 60 * 24 * 30;

	/**
	 * 请求令牌有效期默认 12 小时
	 */
	private final static int accessTokenValiditySeconds = 60 * 60 * 12;

	private final RemoteClientDetailsService clientDetailsService;

	/**
	 * Saves the registered client.
	 *
	 * <p>
	 * IMPORTANT: Sensitive information should be encoded externally from the
	 * implementation, e.g. {@link RegisteredClient#getClientSecret()}
	 *
	 * @param registeredClient the {@link RegisteredClient}
	 */
	@Override
	public void save(RegisteredClient registeredClient) {
	}

	/**
	 * Returns the registered client identified by the provided {@code id}, or
	 * {@code null} if not found.
	 *
	 * @param id the registration identifier
	 * @return the {@link RegisteredClient} if found, otherwise {@code null}
	 */
	@Override
	public RegisteredClient findById(String id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the registered client identified by the provided {@code clientId}, or
	 * {@code null} if not found.
	 * @param clientId the client identifier
	 * @return the {@link RegisteredClient} if found, otherwise {@code null}
	 */

	/**
	 * 重写原生方法支持redis缓存
	 *
	 * @param clientId
	 * @return
	 */
	@Override
	@SneakyThrows
	@Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
	public RegisteredClient findByClientId(String clientId) {

		SysOauthClientDetails clientDetails = RetOps.of(clientDetailsService.getClientDetailsById(clientId))
				.getData()
				.orElseThrow(() -> new OAuth2AuthorizationCodeRequestAuthenticationException(
						new OAuth2Error("客户端查询异常，请检查数据库链接"), null));

		RegisteredClient.Builder builder = RegisteredClient.withId(clientDetails.getClientId())
				.clientId(clientDetails.getClientId())
				.clientSecret(SecurityConstants.NOOP + clientDetails.getClientSecret())
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
// KP findByClientId :数组为null ！！！！！！！
		String[] authorizedGrantTypes = clientDetails.getAuthorizedGrantTypes();
		if (ObjectUtils.isEmpty(authorizedGrantTypes)) {
			String[] temp = {"password"};
			authorizedGrantTypes = temp;
		}
		for (String authorizedGrantType : authorizedGrantTypes) {
			builder.authorizationGrantType(new AuthorizationGrantType(authorizedGrantType));
		}
		// 回调地址
		Optional.ofNullable(clientDetails.getWebServerRedirectUri())
				.ifPresent(redirectUri -> Arrays.stream(redirectUri.split(StrUtil.COMMA))
						.filter(StrUtil::isNotBlank)
						.forEach(builder::redirectUri));

		// scope
		Optional.ofNullable(clientDetails.getScope())
				.ifPresent(scope -> Arrays.stream(scope.split(StrUtil.COMMA))
						.filter(StrUtil::isNotBlank)
						.forEach(builder::scope));

		return builder
				.tokenSettings(TokenSettings.builder()
						.accessTokenFormat(OAuth2TokenFormat.REFERENCE)
						.accessTokenTimeToLive(Duration.ofSeconds(
								Optional.ofNullable(clientDetails.getAccessTokenValidity()).orElse(accessTokenValiditySeconds)))
						.refreshTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(clientDetails.getRefreshTokenValidity())
								.orElse(refreshTokenValiditySeconds)))
						.build())
				.clientSettings(ClientSettings.builder()
						.requireAuthorizationConsent(!BooleanUtil.toBoolean(clientDetails.getAutoapprove()))
						.build())
				.build();

	}

}
