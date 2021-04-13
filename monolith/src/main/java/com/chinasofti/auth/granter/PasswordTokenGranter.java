
package com.chinasofti.auth.granter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.chinasofti.auth.enums.BladeUserEnum;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.utils.DigestUtil;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.system.user.entity.UserInfo;
import com.chinasofti.system.user.service.IUserService;
import org.springframework.stereotype.Component;

/**
 * PasswordTokenGranter
 *
 *  @author Arvin Zhou
 */
@Component
@AllArgsConstructor
@Slf4j
public class PasswordTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "password";

	private IUserService userService;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String tenantId = tokenParameter.getArgs().getStr("tenantId");
		String account = tokenParameter.getArgs().getStr("account");
		String password = tokenParameter.getArgs().getStr("password");
		log.info( "tenantId =" + tenantId + ", account ="+ account + ", password =" + password );
		UserInfo userInfo = null;
		if (Func.isNoneBlank(account, password)) {
			//  获取用户类型
			String userType = tokenParameter.getArgs().getStr("userType");
			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
			if (userType.equals(BladeUserEnum.WEB.getName())) {
				userInfo = userService.userInfo(tenantId, account, DigestUtil.encrypt(password));
			} else if (userType.equals(BladeUserEnum.APP.getName())) {
				userInfo = userService.userInfo(tenantId, account, DigestUtil.encrypt(password));
			} else {
				userInfo = userService.userInfo(tenantId, account, DigestUtil.encrypt(password));
			}
		}
		return userInfo;
	}

}
