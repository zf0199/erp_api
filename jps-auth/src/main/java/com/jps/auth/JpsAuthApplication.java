
package com.jps.auth;

import com.jps.common.feign.annotation.EnablePigFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lengleng
 * @date 2018年06月21日 认证授权中心
 */
@EnablePigFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class JpsAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpsAuthApplication.class, args);
	}

}
