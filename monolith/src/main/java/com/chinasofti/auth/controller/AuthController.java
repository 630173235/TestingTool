
package com.chinasofti.auth.controller;

import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import com.chinasofti.auth.dto.AuthDTO;
import com.chinasofti.auth.granter.ITokenGranter;
import com.chinasofti.auth.granter.TokenGranterBuilder;
import com.chinasofti.auth.granter.TokenParameter;
import com.chinasofti.auth.utils.TokenUtil;
import com.chinasofti.common.cache.CacheNames;
import com.chinasofti.core.launch.constant.AppConstant;
import com.chinasofti.core.secure.AuthInfo;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.support.Kv;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.core.tool.utils.RedisUtil;
import com.chinasofti.core.tool.utils.WebUtil;
import com.chinasofti.system.user.entity.UserInfo;
import com.chinasofti.testing.dto.SaveApiTestCaseRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

/**
 * 认证模块
 *
 * @author Arvin Zhou
 * header Authorization = Basic c2FiZXI6c2FiZXJfc2VjcmV0
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_AUTH_NAME)
@Api(value = "用户授权认证", tags = "授权接口")
public class AuthController {

	private RedisUtil redisUtil;
	
	
	@PostMapping("token")
	@ApiOperation(value = "获取认证token", notes = "传入租户ID:tenantId,账号:account,密码:password")
	public R<AuthInfo> token(@ApiParam(value = "授权类型", required = true) @RequestParam(defaultValue = "password", required = false) String grantType,
							 @ApiParam(value = "刷新令牌") @RequestParam(required = false) String refreshToken,
							 @ApiParam(value = "租户ID", required = true) @RequestParam(defaultValue = "000000", required = false) String tenantId,
							 @ApiParam(value = "账号") @RequestParam(required = false) String account,
							 @ApiParam(value = "密码") @RequestParam(required = false) String password) {

		String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

		TokenParameter tokenParameter = new TokenParameter();
		tokenParameter.getArgs().set("tenantId", tenantId)
			.set("account", account)
			.set("password", password)
			.set("grantType", grantType)
			.set("refreshToken", refreshToken)
			.set("userType", userType);

		ITokenGranter granter = TokenGranterBuilder.getGranter(grantType);
		UserInfo userInfo = granter.grant(tokenParameter);

		if (userInfo == null || userInfo.getUser() == null || userInfo.getUser().getId() == null) {
			return R.fail(TokenUtil.USER_NOT_FOUND);
		}

		return R.data(TokenUtil.createAuthInfo(userInfo));
	}

	@PostMapping("/auth/token")
	@ApiOperation(value = "获取认证token", notes = "传入租户AuthDTO")
	public R<AuthInfo> token(@Valid @RequestBody AuthDTO authDTO) {

		String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

		TokenParameter tokenParameter = new TokenParameter();
		tokenParameter.getArgs().set("tenantId", authDTO.getTenantId())
			.set("account", authDTO.getAccount())
			.set("password", authDTO.getPassword())
			.set("grantType", authDTO.getGrantType())
			.set("refreshToken", authDTO.getRefreshToken())
			.set("userType", userType);

		ITokenGranter granter = TokenGranterBuilder.getGranter(authDTO.getGrantType());
		UserInfo userInfo = granter.grant(tokenParameter);

		if (userInfo == null || userInfo.getUser() == null || userInfo.getUser().getId() == null) {
			return R.fail(TokenUtil.USER_NOT_FOUND);
		}

		return R.data(TokenUtil.createAuthInfo(userInfo));
	}
	
//	@GetMapping("/captcha")
//	@ApiOperation(value = "获取验证码")
//	public R<Kv> captcha() {
//		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
//		String verCode = specCaptcha.text().toLowerCase();
//		String key = UUID.randomUUID().toString();
//		// 存入redis并设置过期时间为30分钟
//		redisUtil.set(CacheNames.CAPTCHA_KEY + key, verCode, 30L, TimeUnit.MINUTES);
//		// 将key和base64返回给前端
//		return R.data(Kv.init().set("key", key).set("image", specCaptcha.toBase64()));
//	}

}
