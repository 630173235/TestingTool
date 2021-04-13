package com.chinasofti.core.changelog.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.chinasofti.core.changelog.handle.DataLogHandler;
import com.chinasofti.core.changelog.handle.DefaultDataLogHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
public class DataChangeLogConfiguration {

	private final JdbcTemplate jdbcTemplate;

	@Bean
	@ConditionalOnMissingBean(DataLogHandler.class)
	public DataLogHandler dataLogHandler() {
		log.debug( "DataLogHandler 初始化" );
		return new DefaultDataLogHandler(jdbcTemplate);
	}

}
