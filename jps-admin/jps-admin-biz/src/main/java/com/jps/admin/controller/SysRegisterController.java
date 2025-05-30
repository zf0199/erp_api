package com.jps.admin.controller;

import com.jps.admin.api.dto.RegisterUserDTO;
import com.jps.admin.service.SysUserService;
import com.jps.common.core.util.R;
import com.jps.common.log.annotation.SysLog;
import com.jps.common.security.annotation.Inner;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lengleng
 * @date 2022/3/30
 * <p>
 * 客户端注册功能 register.user = false
 */
@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "register.user", matchIfMissing = true)
public class SysRegisterController {

	private final SysUserService userService;

	/**
	 * 注册用户
	 * @param registerUserDTO 注册用户 DTO
	 * @return {@link R }<{@link Boolean }>
	 */
	@Inner(value = false)
	@SysLog("注册用户")
	@PostMapping("/user")
	public R<Boolean> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
		return userService.registerUser(registerUserDTO);
	}

}
