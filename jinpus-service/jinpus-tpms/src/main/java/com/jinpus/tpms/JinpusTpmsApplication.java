package com.jinpus.tpms;

import com.pig4cloud.pig.common.feign.annotation.EnablePigFeignClients;
import com.pig4cloud.pig.common.security.annotation.EnablePigResourceServer;
import com.pig4cloud.pig.common.swagger.annotation.EnablePigDoc;
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
@EnablePigResourceServer
public class JinpusTpmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JinpusTpmsApplication.class);
	}
}
