
package com.jps.auth.support.handler;

import cn.hutool.core.util.StrUtil;
import com.jps.admin.api.entity.SysLog;
import com.jps.common.core.constant.CommonConstants;
import com.jps.common.core.util.SpringContextHolder;
import com.jps.common.core.util.WebUtils;
import com.jps.common.log.event.SysLogEvent;
import com.jps.common.log.util.SysLogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * @author zhangran
 * @date 2022-06-02
 *
 * 事件机制处理退出相关
 */
@Slf4j
@Component
public class PigLogoutSuccessEventHandler implements ApplicationListener<LogoutSuccessEvent> {

	@Override
	public void onApplicationEvent(LogoutSuccessEvent event) {
		Authentication authentication = (Authentication) event.getSource();
		if (authentication instanceof PreAuthenticatedAuthenticationToken) {
			handle(authentication);
		}
	}

	/**
	 * 处理退出成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 * @param authentication 登录对象
	 */
	public void handle(Authentication authentication) {
		log.info("用户：{} 退出成功", authentication.getPrincipal());
		SysLog logVo = SysLogUtils.getSysLog();
		logVo.setTitle("退出成功");

		// 设置对应的token
		WebUtils.getRequest().ifPresent(request -> {
			logVo.setParams(request.getHeader(HttpHeaders.AUTHORIZATION));
			// 计算请求耗时
			String startTimeStr = request.getHeader(CommonConstants.REQUEST_START_TIME);
			if (StrUtil.isNotBlank(startTimeStr)) {
				Long startTime = Long.parseLong(startTimeStr);
				Long endTime = System.currentTimeMillis();
				logVo.setTime(endTime - startTime);
			}
		});

		// 这边设置ServiceId
		if (authentication instanceof PreAuthenticatedAuthenticationToken) {
			logVo.setServiceId(authentication.getCredentials().toString());
		}
		logVo.setCreateBy(authentication.getName());
		// 发送异步日志事件
		SpringContextHolder.publishEvent(new SysLogEvent(logVo));
	}

}
