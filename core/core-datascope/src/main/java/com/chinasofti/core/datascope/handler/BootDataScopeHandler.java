package com.chinasofti.core.datascope.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.plugin.Invocation;
import com.chinasofti.core.datascope.constant.DataScopeConstant;
import com.chinasofti.core.datascope.enums.DataScopeEnum;
import com.chinasofti.core.datascope.interceptor.DataScopeInterceptor;
import com.chinasofti.core.datascope.model.DataScopeModel;
import com.chinasofti.core.secure.BootUser;
import com.chinasofti.core.tool.constant.RoleConstant;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.core.tool.utils.PlaceholderUtil;
import com.chinasofti.core.tool.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 默认数据权限规则
 */
@Slf4j
@RequiredArgsConstructor
public class BootDataScopeHandler implements DataScopeHandler {

	private final ScopeModelHandler scopeModelHandler;

	@Override
	public String sqlCondition(String mapperId, DataScopeModel dataScope, BootUser bootUser, String originalSql) {

		//数据权限资源编号
		String code = dataScope.getResourceCode();

		//根据mapperId从数据库中获取对应模型
		DataScopeModel dataScopeDb = scopeModelHandler.getDataScopeByMapper(mapperId, bootUser.getRoleId());

		//mapperId配置未取到则从数据库中根据资源编号获取
		if (dataScopeDb == null && StringUtil.isNotBlank(code)) {
			dataScopeDb = scopeModelHandler.getDataScopeByCode(code);
		}

		//未从数据库找到对应配置则采用默认
		dataScope = (dataScopeDb != null) ? dataScopeDb : dataScope;

		//判断数据权限类型并组装对应Sql
		Integer scopeRule = Objects.requireNonNull(dataScope).getScopeType();
		DataScopeEnum scopeTypeEnum = DataScopeEnum.of(scopeRule);
		
		List<Long> ids = new ArrayList<>();
		String whereSql = "where scope.{} in ({})";
		if (DataScopeEnum.ALL == scopeTypeEnum || StringUtil.containsAny(bootUser.getRoleName(), RoleConstant.ADMIN)) {
			log.debug( "scopeTypeEnum = " + scopeTypeEnum);
			log.debug( "该用户是超级管理员，可看所有数据");
			return null;
		} else if (DataScopeEnum.CUSTOM == scopeTypeEnum) {
			whereSql = PlaceholderUtil.getDefaultResolver().resolveByMap(dataScope.getScopeValue(), BeanUtil.toMap(bootUser));
		} else if (DataScopeEnum.OWN == scopeTypeEnum) {
			dataScope.setScopeColumn( DataScopeConstant.DEFAULE_USERID_COLUMN );
			ids.add(bootUser.getUserId());
		} else if (DataScopeEnum.OWN_DEPT == scopeTypeEnum) {
			ids.addAll(Func.toLongList(bootUser.getDeptId()));
		} else if (DataScopeEnum.OWN_DEPT_CHILD == scopeTypeEnum) {
			List<Long> deptIds = Func.toLongList(bootUser.getDeptId());
			ids.addAll(deptIds);
			deptIds.forEach(deptId -> {
				List<Long> deptIdList = scopeModelHandler.getDeptAncestors(deptId);
				ids.addAll(deptIdList);
			});
		}
		log.debug( "whereSql = " +whereSql );
		log.debug( "originalSql = " + originalSql );
		
		//select * from (SELECT * FROM chinasofti_demo WHERE is_deleted = 0) scope where scope.create_dept in (1123598821738675202)
		
		return StringUtil.format(" select {} from ({}) scope " + whereSql, Func.toStr(dataScope.getScopeField(), "*"), originalSql, dataScope.getScopeColumn(), StringUtil.join(ids));
	}

}
