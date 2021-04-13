package com.chinasofti.auth.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * @ApiParam(value = "授权类型", required = true) @RequestParam(defaultValue = "password", required = false) String grantType,
							 @ApiParam(value = "刷新令牌") @RequestParam(required = false) String refreshToken,
							 @ApiParam(value = "租户ID", required = true) @RequestParam(defaultValue = "000000", required = false) String tenantId,
							 @ApiParam(value = "账号") @RequestParam(required = false) String account,
							 @ApiParam(value = "密码") @RequestParam(required = false) String password
 * @author Arvin
 *
 */
@Data
public class AuthDTO implements Serializable{

	private String grantType = "password";
	
	private String refreshToken;
	
	private String tenantId = "000000";
	
	private String account;
	
	private String password;
}
