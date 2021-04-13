
package com.chinasofti.core.boot.tenant;

/**
 * 租户id生成器
 *
 * @author Arvin Zhou
 */
public interface TenantId {

	/**
	 * 生成自定义租户id
	 *
	 * @return string
	 */
	String generate();

}
