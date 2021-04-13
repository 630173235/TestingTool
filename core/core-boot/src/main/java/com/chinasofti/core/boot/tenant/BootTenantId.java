
package com.chinasofti.core.boot.tenant;

import com.chinasofti.core.tool.utils.RandomType;
import com.chinasofti.core.tool.utils.StringUtil;

/**
 * blade租户id生成器
 *
 * @author Arvin Zhou
 */
public class BootTenantId implements TenantId {
	@Override
	public String generate() {
		return StringUtil.random(6, RandomType.INT);
	}
}
