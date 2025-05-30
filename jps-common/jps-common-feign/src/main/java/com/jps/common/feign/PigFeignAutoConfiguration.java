

package com.jps.common.feign;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.jps.common.feign.core.PigFeignInnerRequestInterceptor;
import com.jps.common.feign.sentinel.ext.PigSentinelFeign;
import com.jps.common.feign.core.PigFeignRequestCloseInterceptor;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.PigFeignClientsRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * sentinel 配置
 *
 * @author lengleng
 * @date 2020-02-12
 */
@Configuration(proxyBeanMethods = false)
@Import(PigFeignClientsRegistrar.class)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class PigFeignAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "feign.sentinel.enabled")
	public Feign.Builder feignSentinelBuilder() {
		return PigSentinelFeign.builder();
	}

	/**
	 * add http connection close header
	 * @return
	 */
	@Bean
	public PigFeignRequestCloseInterceptor pigFeignRequestCloseInterceptor() {
		return new PigFeignRequestCloseInterceptor();
	}

	/**
	 * add inner request header
	 * @return PigFeignInnerRequestInterceptor
	 */
	@Bean
	public PigFeignInnerRequestInterceptor pigFeignInnerRequestInterceptor() {
		return new PigFeignInnerRequestInterceptor();
	}

}
