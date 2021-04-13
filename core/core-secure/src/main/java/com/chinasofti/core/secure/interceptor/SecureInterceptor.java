
package com.chinasofti.core.secure.interceptor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.chinasofti.core.secure.utils.SecureUtil;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.api.ResultCode;
import com.chinasofti.core.tool.constant.BootConstant;
import com.chinasofti.core.tool.jackson.JsonUtil;
import com.chinasofti.core.tool.utils.WebUtil;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * jwt拦截器校验
 *
 * @author Arvin Zhou
 */
@Slf4j
@AllArgsConstructor
public class SecureInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (null != SecureUtil.getUser()) {
			return true;
		} else {
			log.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request), JsonUtil.toJson(request.getParameterMap()));
			R result = R.fail(ResultCode.UN_AUTHORIZED);
			response.setCharacterEncoding(BootConstant.UTF_8);
			response.setHeader(BootConstant.CONTENT_TYPE_NAME, MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_OK);
			try {
				response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
			} catch (IOException ex) {
				log.error(ex.getMessage());
			}
			return false;
		}
	}

}
