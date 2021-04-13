package com.chinasofti.core.datascope.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.chinasofti.core.datascope.interceptor.DataScopeInterceptor;
import com.chinasofti.core.datascope.props.DataScopeProperties;
import com.chinasofti.core.datascope.handler.BootDataScopeHandler;
import com.chinasofti.core.datascope.handler.BootScopeModelHandler;
import com.chinasofti.core.datascope.handler.DataScopeHandler;
import com.chinasofti.core.datascope.handler.ScopeModelHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据权限配置类
 */
@Slf4j
@Configuration
@AllArgsConstructor
//@EnableConfigurationProperties(DataScopeProperties.class)
public class DataScopeConfiguration {

	private final JdbcTemplate jdbcTemplate;

	@Bean
	@ConditionalOnMissingBean(ScopeModelHandler.class)
	public ScopeModelHandler scopeModelHandler() {
		log.debug( "ScopeModelHandler 初始化" );
		return new BootScopeModelHandler(jdbcTemplate);
	}

	@Bean
	@ConditionalOnBean(ScopeModelHandler.class)
	@ConditionalOnMissingBean(DataScopeHandler.class)
	public DataScopeHandler dataScopeHandler(ScopeModelHandler scopeModelHandler) {
		log.debug( "DataScopeHandler 初始化" );
		return new BootDataScopeHandler(scopeModelHandler);
	}

	@Bean
	@ConditionalOnBean(DataScopeHandler.class)
	@ConditionalOnMissingBean(DataScopeInterceptor.class)
	public DataScopeInterceptor interceptor(DataScopeHandler dataScopeHandler) {
		log.debug( "DataScopeInterceptor 初始化" );
		return new DataScopeInterceptor(dataScopeHandler);
	}

}
