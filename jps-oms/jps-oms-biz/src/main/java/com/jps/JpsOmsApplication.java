package com.jps;

import com.jps.common.feign.annotation.EnablePigFeignClients;
import com.jps.common.security.annotation.EnableResourceServer;
import com.jps.common.swagger.annotation.EnablePigDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @className: OmsBizApplication
 * @author: zf
 * @date: 2025/5/14 14:20
 * @version: 1.0
 * @description:
 */
@SpringBootApplication
// 服务发现
@EnableDiscoveryClient
@EnablePigDoc(value = "oms")
@EnablePigFeignClients
@EnableResourceServer
public class JpsOmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(JpsOmsApplication.class);
	}
}
