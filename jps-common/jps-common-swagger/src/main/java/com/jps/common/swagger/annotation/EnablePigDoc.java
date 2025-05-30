

package com.jps.common.swagger.annotation;

import com.jps.common.swagger.support.SwaggerProperties;
import com.jps.common.core.factory.YamlPropertySourceFactory;
import com.jps.common.swagger.config.OpenAPIDefinitionImportSelector;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.lang.annotation.*;

/**
 * 开启 pig spring doc
 *
 * @author lengleng
 * @date 2022-03-26
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
@Import(OpenAPIDefinitionImportSelector.class)
@PropertySource(value = "classpath:openapi-config.yaml", factory = YamlPropertySourceFactory.class)
public @interface EnablePigDoc {

	/**
	 * 网关路由前缀
	 * @return String
	 */
	String value();

	/**
	 * 是否是微服务架构
	 * @return true
	 */
	boolean isMicro() default true;

}
