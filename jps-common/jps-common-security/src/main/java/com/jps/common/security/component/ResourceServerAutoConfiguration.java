

package com.jps.common.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authorization.method.PrePostTemplateDefaults;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * @author lengleng
 * @date 2022-06-02
 */
@RequiredArgsConstructor
@EnableConfigurationProperties(PermitAllUrlProperties.class)
public class ResourceServerAutoConfiguration {

	/**
	 * 鉴权具体的实现逻辑
	 * @return （#pms.xxx）
	 */
	@Bean("pms")
	public PermissionService permissionService() {
		return new PermissionService();
	}

	/**
	 * 请求令牌的抽取逻辑
	 * @param urlProperties 对外暴露的接口列表
	 * @return BearerTokenExtractor
	 */
	@Bean
	public PigBearerTokenExtractor pigBearerTokenExtractor(PermitAllUrlProperties urlProperties) {
		return new PigBearerTokenExtractor(urlProperties);
	}

	/**
	 * 资源服务器异常处理
	 * @param objectMapper jackson 输出对象
	 * @param securityMessageSource 自定义国际化处理器
	 * @return ResourceAuthExceptionEntryPoint
	 */
	@Bean
	public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper,
			MessageSource securityMessageSource) {
		return new ResourceAuthExceptionEntryPoint(objectMapper, securityMessageSource);
	}

	/**
	 * 资源服务器toke内省处理器
	 * @param authorizationService token 存储实现
	 * @return TokenIntrospector
	 */
	@Bean
	public OpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2AuthorizationService authorizationService) {
		return new PigCustomOpaqueTokenIntrospector(authorizationService);
	}

	/**
	 * 支持自定义权限表达式
	 * @return {@link PrePostTemplateDefaults }
	 */
	@Bean
	PrePostTemplateDefaults prePostTemplateDefaults() {
		return new PrePostTemplateDefaults();
	}

}
