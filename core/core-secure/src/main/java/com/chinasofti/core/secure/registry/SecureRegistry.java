
package com.chinasofti.core.secure.registry;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * secure api放行配置
 *
 * @author Arvin Zhou
 */
@Data
public class SecureRegistry {

	private boolean enabled = true;

	private final List<String> defaultExcludePatterns = new ArrayList<>();

	private final List<String> excludePatterns = new ArrayList<>();

	public SecureRegistry() {
		this.defaultExcludePatterns.add("/actuator/health/**");
		this.defaultExcludePatterns.add("/v2/api-docs/**");
		this.defaultExcludePatterns.add("/micro-auth/auth/**");
		this.defaultExcludePatterns.add("/micro-auth/token/**");
		this.defaultExcludePatterns.add("/log/**");
		this.defaultExcludePatterns.add("/micro-user/user/user-info");
		this.defaultExcludePatterns.add("/micro-user/user/user-info-by-id");
		this.defaultExcludePatterns.add("/micro-system/menu/auth-routes");
		this.defaultExcludePatterns.add("/error/**");
		this.defaultExcludePatterns.add("/assets/**");
		this.defaultExcludePatterns.add("/doc.html");
		this.defaultExcludePatterns.add("/swagger-resources/**");
		this.defaultExcludePatterns.add("/webjars/**");
		
		
	}

	/**
	 * 设置放行api
	 */
	public SecureRegistry excludePathPatterns(String... patterns) {
		return excludePathPatterns(Arrays.asList(patterns));
	}

	/**
	 * 设置放行api
	 */
	public SecureRegistry excludePathPatterns(List<String> patterns) {
		this.excludePatterns.addAll(patterns);
		return this;
	}

}
