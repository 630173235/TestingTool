
package com.chinasofti.core.launch.service;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

/**
 * launcher 扩展 用于一些组件发现
 *
 * @author Arvin Zhou
 */
public interface LauncherService extends Ordered, Comparable<LauncherService>  {

	/**
	 * 启动时 处理 SpringApplicationBuilder
	 *
	 * @param builder SpringApplicationBuilder
	 * @param appName SpringApplicationAppName
	 * @param profile SpringApplicationProfile
	 */
	void launcher(SpringApplicationBuilder builder, String appName, String profile);

	/**
	 * 获取排列顺序
	 *
	 * @return order
	 */
	@Override
	default int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	/**
	 * 对比排序
	 *
	 * @param o LauncherService
	 * @return compare
	 */
	@Override
	default int compareTo(LauncherService o) {
		return Integer.compare(this.getOrder(), o.getOrder());
	}

}
