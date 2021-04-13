package com.chinasofti.core.datascope.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import com.chinasofti.core.datascope.annotation.DataAuth;
import com.chinasofti.core.datascope.handler.DataScopeHandler;
import com.chinasofti.core.datascope.model.DataScopeModel;
import com.chinasofti.core.datascope.props.DataScopeProperties;
import com.chinasofti.core.intercept.QueryInterceptor;
import com.chinasofti.core.secure.BootUser;
import com.chinasofti.core.secure.utils.SecureUtil;
import com.chinasofti.core.tool.utils.ClassUtil;
import com.chinasofti.core.tool.utils.SpringUtil;
import com.chinasofti.core.tool.utils.StringUtil;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Slf4j
@RequiredArgsConstructor
public class DataScopeInterceptor implements QueryInterceptor {

	private final ConcurrentMap<String, DataAuth> dataAuthMap = new ConcurrentHashMap<>(8);

	private final DataScopeHandler dataScopeHandler;
	private final DataScopeProperties dataScopeProperties = new DataScopeProperties();

	@Override
	public void intercept(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
		//未启用则放行
		//log.debug( "数据权限 = " + dataScopeProperties.getEnabled() );
		if (!dataScopeProperties.getEnabled()) {
			return;
		}

		//未取到用户则放行
		BootUser bootUser = SecureUtil.getUser();
		//log.debug( "当前用户 = " + bladeUser.toString() );
		if (bootUser == null) {
			return;
		}
        //log.debug( "Sql command type = "+  ms.getSqlCommandType() );
		if (SqlCommandType.SELECT != ms.getSqlCommandType() || StatementType.CALLABLE == ms.getStatementType()) {
			return;
		}

		String originalSql = boundSql.getSql();

		//查找注解中包含DataAuth类型的参数
		DataAuth dataAuth = findDataAuthAnnotation(ms);

		//注解为空并且数据权限方法名未匹配到,则放行
		String mapperId = ms.getId();
		String className = mapperId.substring(0, mapperId.lastIndexOf(StringPool.DOT));
		String mapperName = ClassUtil.getShortName(className);
		String methodName = mapperId.substring(mapperId.lastIndexOf(StringPool.DOT) + 1);
		boolean mapperSkip = dataScopeProperties.getMapperKey().stream().noneMatch(methodName::contains)
			|| dataScopeProperties.getMapperExclude().stream().anyMatch(mapperName::contains);
		if (dataAuth == null && mapperSkip) {
			return;
		}

		//创建数据权限模型
		DataScopeModel dataScope = new DataScopeModel();
       
		//若注解不为空,则配置注解项
		if (dataAuth != null) {
			dataScope.setResourceCode(dataAuth.code());
			dataScope.setScopeColumn(dataAuth.column());
			dataScope.setScopeType(dataAuth.type().getType());
			dataScope.setScopeField(dataAuth.field());
			dataScope.setScopeValue(dataAuth.value());
		}
        log.debug( "dataScope = " + dataScope.toString() );
		//获取数据权限规则对应的筛选Sql
		String sqlCondition = dataScopeHandler.sqlCondition(mapperId, dataScope, bootUser, originalSql);
		log.debug( "sqlCondition = " +sqlCondition );
		if (!StringUtil.isBlank(sqlCondition)) {
			PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
			mpBoundSql.sql(sqlCondition);
		}
	}

	/**
	 * 获取数据权限注解信息
	 *
	 * @param mappedStatement mappedStatement
	 * @return DataAuth
	 */
	private DataAuth findDataAuthAnnotation(MappedStatement mappedStatement) {
		String id = mappedStatement.getId();
		return dataAuthMap.computeIfAbsent(id, (key) -> {
			String className = key.substring(0, key.lastIndexOf(StringPool.DOT));
			String mapperBean = StringUtil.firstCharToLower(ClassUtil.getShortName(className));
			log.debug( "mapperBean = " + mapperBean );
			Object mapper = SpringUtil.getBean(mapperBean);
			String methodName = key.substring(key.lastIndexOf(StringPool.DOT) + 1);
			Class<?>[] interfaces = ClassUtil.getAllInterfaces(mapper);
			for (Class<?> mapperInterface : interfaces) {
				for (Method method : mapperInterface.getDeclaredMethods()) {
					if (methodName.equals(method.getName()) && method.isAnnotationPresent(DataAuth.class)) {
						return method.getAnnotation(DataAuth.class);
					}
				}
			}
			return null;
		});
	}

}
