package com.chinasofti.core.datascope.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.chinasofti.core.datascope.constant.DataScopeConstant;
import com.chinasofti.core.datascope.model.DataScopeModel;
import com.chinasofti.core.tool.utils.CollectionUtil;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.core.tool.utils.StringPool;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BladeScopeModelHandler
 */
@Slf4j
@RequiredArgsConstructor
public class BootScopeModelHandler implements ScopeModelHandler {

	private static final String SCOPE_CACHE_CODE = "dataScope:code:";
	private static final String SCOPE_CACHE_CLASS = "dataScope:class:";
	private static final String DEPT_CACHE_ANCESTORS = "dept:ancestors:";

	private final JdbcTemplate jdbcTemplate;

	/**
	 * 获取数据权限
	 *
	 * @param mapperId 数据权限mapperId
	 * @param roleId   用户角色集合
	 * @return DataScopeModel
	 */
	@Override
	public DataScopeModel getDataScopeByMapper(String mapperId, String roleId) {
//		List<Object> args = new ArrayList<>(Collections.singletonList(mapperId));
//		List<Long> roleIds = Func.toLongList(roleId);
//		args.addAll(roleIds);
//		DataScopeModel dataScope=null;
//		List<DataScopeModel> list = jdbcTemplate.query(DataScopeConstant.dataByMapper(roleIds.size()), args.toArray(), new BeanPropertyRowMapper<>(DataScopeModel.class));
//		if (CollectionUtil.isNotEmpty(list)) {
//			dataScope = list.iterator().next();
//		}
//		return dataScope;
		return null;
	}

	/**
	 * 获取数据权限
	 *
	 * @param code 数据权限资源编号
	 * @return DataScopeModel
	 */
	@Override
	public DataScopeModel getDataScopeByCode(String code) {
//		DataScopeModel dataScope = null;
//		List<DataScopeModel> list = jdbcTemplate.query(DataScopeConstant.DATA_BY_CODE, new Object[]{code}, new BeanPropertyRowMapper<>(DataScopeModel.class));
//		if (CollectionUtil.isNotEmpty(list)) {
//			dataScope = list.iterator().next();
//		}
//		return dataScope;
		return null;
	}

	/**
	 * 获取部门子级
	 *
	 * @param deptId 部门id
	 * @return deptIds
	 */
	@Override
	public List<Long> getDeptAncestors(Long deptId) {
		List<Long> ancestors = jdbcTemplate.queryForList(DataScopeConstant.DATA_BY_DEPT, new Object[]{deptId}, Long.class);
		log.debug( "ancestors = " + ancestors.toString() );
		return ancestors;
	}
}
