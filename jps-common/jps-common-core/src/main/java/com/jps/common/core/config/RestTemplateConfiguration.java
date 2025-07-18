/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jps.common.core.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

/**
 * @author lengleng
 * @date 2019/2/1 RestTemplate
 */
@AutoConfiguration
public class RestTemplateConfiguration {

	/**
	 * 动态 REST 模板
	 * @return {@link RestTemplate }
	 */
	@Bean
	@LoadBalanced
	@ConditionalOnProperty(value = "spring.cloud.nacos.discovery.enabled", havingValue = "true", matchIfMissing = true)
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * REST 客户端构建器（支持负载均衡）
	 * @return {@link RestClient.Builder }
	 */
	@Bean
	@LoadBalanced
	@ConditionalOnProperty(value = "spring.cloud.nacos.discovery.enabled", havingValue = "true", matchIfMissing = true)
	RestClient.Builder restClientBuilder() {
		return RestClient.builder();
	}

	@Bean
	public RestTemplate directRestTemplate() {
		return new RestTemplate(); // 注意：没有 @LoadBalanced 注解
	}

}
