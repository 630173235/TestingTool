
package com.chinasofti.core.tool.support.xss;

import lombok.AllArgsConstructor;
import com.chinasofti.core.tool.utils.StringPool;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤
 *
 * @author Arvin Zhou
 */
@AllArgsConstructor
public class XssFilter implements Filter {

	private XssProperties xssProperties;
	private XssUrlProperties xssUrlProperties;

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getServletPath();
		if (isSkip(path)) {
			chain.doFilter(request, response);
		} else {
			XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(xssRequest, response);
		}
	}

	private boolean isSkip(String path) {
		return (xssUrlProperties.getExcludePatterns().stream().anyMatch(path::startsWith))
			|| (xssProperties.getSkipUrl().stream().map(url -> url.replace("/**", StringPool.EMPTY)).anyMatch(path::startsWith));
	}

	@Override
	public void destroy() {

	}

}
