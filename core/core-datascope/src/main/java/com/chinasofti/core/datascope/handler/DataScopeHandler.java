package com.chinasofti.core.datascope.handler;

import org.apache.ibatis.plugin.Invocation;
import com.chinasofti.core.datascope.model.DataScopeModel;
import com.chinasofti.core.secure.BootUser;

/**
 * 数据权限规则
 */
public interface DataScopeHandler {

	/**
	 * 获取过滤sql
	 *
	 * @param mapperId    数据查询类
	 * @param dataScope   数据权限类
	 * @param bootUser   当前用户信息
	 * @param originalSql 原始Sql
	 * @return sql
	 */
	String sqlCondition(String mapperId, DataScopeModel dataScope, BootUser bootUser, String originalSql);

}
