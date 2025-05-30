package com.jps.tpms;

import com.jps.common.feign.annotation.EnablePigFeignClients;
import com.jps.common.security.annotation.EnableResourceServer;
import com.jps.common.swagger.annotation.EnablePigDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @className: jinpusTpmsApplication
 * @author: zf
 * @date: 2025/3/5 15:15
 * @version: 1.0
 * @description:
 */
@SpringBootApplication
// 服务发现
@EnableDiscoveryClient
@EnablePigDoc(value = "admin")
@EnablePigFeignClients
@EnableResourceServer
public class JpsTpmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpsTpmsApplication.class);
	}
}
