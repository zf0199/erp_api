

package com.jps.admin.controller;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jps.admin.mapper.SysCompanyMapper;
import com.jps.admin.api.entity.SysCompanyDo;
import com.jps.admin.api.feign.RemoteTokenService;
import com.jps.common.core.util.R;
import com.jps.common.log.annotation.SysLog;
import com.jps.common.security.annotation.HasPermission;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * @author lengleng
 * @date 2018/9/4 getTokenPage 管理
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/sys-token")
@Tag(description = "token", name = "令牌管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysTokenController {



	private final  ValueOperations<String, String> valueOps;
	private final SysCompanyMapper sysCompanyMapper;

	private final RemoteTokenService remoteTokenService;
	private final WebClient webClient = WebClient.create();

	@Qualifier("directRestTemplate")
	@Autowired
	private  RestTemplate restTemplate;

//	private final RestTemplate restTemplate;

	private final String url = "localhost";
	private final String  basic = "Basic cGlnOnBpZw==";


	/**
	 * 分页token 信息
	 * @param params 参数集
	 * @return token集合
	 */

	@PostMapping("/login")
	public R token(@RequestBody(required = false) Map<String, String> params) {
		String no = params.get("no");
		if ("1".equals(no) ){
			MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
			formData.add("username", params.get("username"));
			formData.add("password", params.get("password"));
			formData.add("grant_type", params.get("grant_type"));
			formData.add("scope", params.get("scope"));
			// 设置请求头
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("Authorization",params.get("Authorization"));
			// 创建请求实体
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
            //String s = restTemplate.postForObject( "http://192.168.1.119:9999/auth/oauth2/token", request, String.class);
			JSONObject s = restTemplate.postForObject( "http://localhost:3000/oauth2/token", request, JSONObject.class);
			return R.ok(s);
		}

		Assert.isTrue(StringUtils.isNotBlank(no), " no 参数不合法");
		String companyAddress = valueOps.get(no);
		if (null == companyAddress) {
			LambdaQueryWrapper<SysCompanyDo> sysCompanyDoWrapper = Wrappers.lambdaQuery();
			sysCompanyDoWrapper.eq(SysCompanyDo::getCompanyNo,no);
			SysCompanyDo sysCompanyDo = sysCompanyMapper.selectOne(sysCompanyDoWrapper);
			companyAddress = sysCompanyDo.getServerAddressPro();
			valueOps.set(no,sysCompanyDo.getServerAddressPro());
		}else {
			log.info(">>> {} 命中缓存 服务器地址为{}",no,companyAddress);
		}


//		RestTemplate restTemplate = new RestTemplate();

		// 表单参数
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//		formData.add("username", "admin");
//		formData.add("password", "nDMjfAgz");
//		formData.add("grant_type", "password");
//		formData.add("scope", "server");

		formData.add("username", params.get("username"));
		formData.add("password", params.get("password"));
		formData.add("grant_type", params.get("grant_type"));
		formData.add("scope", params.get("scope"));
		// 设置请求头
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization",params.get("Authorization"));
		// 创建请求实体
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
//		String s = restTemplate.postForObject( "http://192.168.1.119:9999/auth/oauth2/token", request, String.class);
		JSONObject s = restTemplate.postForObject( "http://192.168.1.119:9999/auth/oauth2/token", request, JSONObject.class);


		// 创建表单参数
//		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//		formData.add("grant_type", "password");
//		formData.add("username", "admin");
//		formData.add("password", "nDMjfAgz");
//		formData.add("scope", "server");

//		// "http://"+url+":3000"+"/oauth2/token"
//		Map block = webClient.post()
//				.uri("http://" + url + ":3000" + "/oauth2/token")
//				.headers(headers -> {
//					headers.setBasicAuth(HttpHeaders.AUTHORIZATION, basic);
//					headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//				})
//				.bodyValue(formData)
//				.retrieve()
//				.bodyToMono(Map.class)
//				.block();

		return R.ok(s);

	}







	/**
	 * 分页token 信息
	 * @param params 参数集
	 * @return token集合
	 */
	@RequestMapping("/page")
	public R getTokenPage(@RequestBody Map<String, Object> params) {
		return remoteTokenService.getTokenPage(params);
	}

	/**
	 * 删除
	 * @param tokens tokens
	 * @return success/false
	 */
	@SysLog("删除用户token")
	@DeleteMapping("/delete")
	@HasPermission("sys_token_del")
	public R removeById(@RequestBody String[] tokens) {
		for (String token : tokens) {
			remoteTokenService.removeTokenById(token);
		}
		return R.ok();
	}

}
