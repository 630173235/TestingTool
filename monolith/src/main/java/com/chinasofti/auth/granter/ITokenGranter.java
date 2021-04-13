
package com.chinasofti.auth.granter;


import com.chinasofti.system.user.entity.UserInfo;

/**
 * 授权认证统一接口.
 *
 * @author Arvin Zhou
 */
public interface ITokenGranter {

	/**
	 * 获取用户信息
	 *
	 * @param tokenParameter 授权参数
	 * @return UserInfo
	 */
	UserInfo grant(TokenParameter tokenParameter);

}
