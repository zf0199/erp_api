

package com.jps.admin;

import com.jps.common.feign.annotation.EnablePigFeignClients;
import com.jps.common.security.annotation.EnableResourceServer;
import com.jps.common.swagger.annotation.EnablePigDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lengleng
 * @date 2018年06月21日
 * <p>
 * 用户统一管理系统
 */
@EnablePigDoc(value = "admin")
@EnablePigFeignClients
@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class JpsAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(JpsAdminApplication.class, args);
	}

}
