

package com.jps.auth.config;

import com.jps.auth.support.CustomeOAuth2AccessTokenGenerator;
import com.jps.auth.support.core.CustomeOAuth2TokenCustomizer;
import com.jps.auth.support.core.FormIdentityLoginConfigurer;
import com.jps.auth.support.core.PigDaoAuthenticationProvider;
import com.jps.auth.support.filter.LoginOriginFilter;
import com.jps.auth.support.filter.PasswordDecoderFilter;
import com.jps.auth.support.filter.ValidateCodeFilter;
import com.jps.auth.support.handler.FAuthenticationFailureHandler;
import com.jps.auth.support.handler.FAuthenticationSuccessHandler;
import com.jps.auth.support.password.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import com.jps.auth.support.password.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import com.jps.common.core.constant.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeRequestAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

/**
 * @className: JpsAuthenticationSuccessEventHandler
 * @author: zf
 * @date: 2025/5/14 16:03
 * @version: 1.0
 * @description: 认证服务器核心配置类
 */
@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfiguration {

	// 用于保存/加载授权信息 比如access token refresh token
	private final OAuth2AuthorizationService authorizationService;

	// 自定义密码解密过滤器 处理前端密码解码逻辑
	private final PasswordDecoderFilter passwordDecoderFilter;

	// 自定义验证码校验过滤器
	private final ValidateCodeFilter validateCodeFilter;

	private final LoginOriginFilter loginOriginFilter;

	/**
	 * Authorization Server 配置，仅对 /oauth2/** 的请求有效
	 *
	 * @param http http
	 * @return {@link SecurityFilterChain }
	 * @throws Exception 异常
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE) // 此过滤器优先级最高
	public SecurityFilterChain authorizationServer(HttpSecurity http) throws Exception {
		// 配置授权服务器的安全策略，只有/oauth2/**的请求才会走如下的配置
		http.securityMatcher("/oauth2/**"); // 匹配路径

		// 创建授权服务器配置类 配置Authorization Server的核心类
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

		// 增加验证码过滤器
		http.addFilterBefore(loginOriginFilter, UsernamePasswordAuthenticationFilter.class);
		// 增加验证码过滤器
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
		// 增加密码解密过滤器
		http.addFilterBefore(passwordDecoderFilter, UsernamePasswordAuthenticationFilter.class);

		http.with(
				authorizationServerConfigurer.tokenEndpoint(
						(tokenEndpoint) -> {// 个性化认证授权端点
							tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter()) // 注入自定义的授权认证Converter
									.accessTokenResponseHandler(new FAuthenticationSuccessHandler()) // 登录成功处理器
									.errorResponseHandler(new FAuthenticationFailureHandler());// 登录失败处理器
						}
				).clientAuthentication(
						oAuth2ClientAuthenticationConfigurer -> // 个性化客户端认证
								oAuth2ClientAuthenticationConfigurer.errorResponseHandler(new FAuthenticationFailureHandler()) // 处理客户端认证异常
				).authorizationEndpoint(
						authorizationEndpoint ->
								authorizationEndpoint.consentPage(SecurityConstants.CUSTOM_CONSENT_PAGE_URI) // 授权码端点个性化confirm页面
				),
				Customizer.withDefaults()
		).authorizeHttpRequests(
				authorizeRequests ->
						authorizeRequests.anyRequest().authenticated()
		);

		// 设置 Token 存储的策略
		http.with(
				authorizationServerConfigurer.authorizationService(authorizationService)// redis存储token的实现
						.authorizationServerSettings(
								AuthorizationServerSettings.builder().issuer(SecurityConstants.PROJECT_LICENSE).build()
						),
				Customizer.withDefaults()
		);

		// 设置授权码模式登录页面
		http.with(new FormIdentityLoginConfigurer(), Customizer.withDefaults());
		DefaultSecurityFilterChain securityFilterChain = http.build();

		// 注入自定义授权模式实现
		addCustomOAuth2GrantAuthenticationProvider(http);

		return securityFilterChain;
	}

	/**
	 * 令牌生成规则实现 </br>
	 * client:username:uuid
	 *
	 * @return OAuth2TokenGenerator
	 */
	@Bean
	public OAuth2TokenGenerator oAuth2TokenGenerator() {
		CustomeOAuth2AccessTokenGenerator accessTokenGenerator = new CustomeOAuth2AccessTokenGenerator();
		// 注入Token 增加关联用户信息
		accessTokenGenerator.setAccessTokenCustomizer(new CustomeOAuth2TokenCustomizer());
		return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, new OAuth2RefreshTokenGenerator());
	}

	/**
	 * request -> xToken 注入请求转换器
	 *
	 * @return DelegatingAuthenticationConverter
	 */
	@Bean
	public AuthenticationConverter accessTokenRequestConverter() {
		return new DelegatingAuthenticationConverter(
				Arrays.asList(
						new OAuth2ResourceOwnerPasswordAuthenticationConverter(),
//						new OAuth2ResourceOwnerSmsAuthenticationConverter(),
						new OAuth2RefreshTokenAuthenticationConverter(),
						new OAuth2ClientCredentialsAuthenticationConverter(),
						new OAuth2AuthorizationCodeAuthenticationConverter(),
						new OAuth2AuthorizationCodeRequestAuthenticationConverter()
				)
		);
	}

	/**
	 * 注入授权模式实现提供方
	 * <p>
	 * 1. 密码模式 </br>
	 * 2. 短信登录 </br>
	 */
	@SuppressWarnings("unchecked")
	private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http) {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);

		OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider = new OAuth2ResourceOwnerPasswordAuthenticationProvider(
				authenticationManager, authorizationService, oAuth2TokenGenerator());

//		OAuth2ResourceOwnerSmsAuthenticationProvider resourceOwnerSmsAuthenticationProvider = new OAuth2ResourceOwnerSmsAuthenticationProvider(
//				authenticationManager, authorizationService, oAuth2TokenGenerator());

		// 处理 UsernamePasswordAuthenticationToken
		http.authenticationProvider(new PigDaoAuthenticationProvider());
		// 处理 OAuth2ResourceOwnerPasswordAuthenticationToken
		http.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);
		// 处理 OAuth2ResourceOwnerSmsAuthenticationToken
//		http.authenticationProvider(resourceOwnerSmsAuthenticationProvider);
	}

}
