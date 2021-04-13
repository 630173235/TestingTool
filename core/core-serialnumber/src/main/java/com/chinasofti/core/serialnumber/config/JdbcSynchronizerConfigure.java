package com.chinasofti.core.serialnumber.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import com.chinasofti.core.serialnumber.persistent.SeqSynchronizer;
import com.chinasofti.core.serialnumber.persistent.provider.MySqlSynchronizer;
import com.chinasofti.core.serialnumber.persistent.provider.OracleSynchronizer;

import lombok.AllArgsConstructor;

import javax.sql.DataSource;


@Configuration
@AllArgsConstructor
public class JdbcSynchronizerConfigure {

	private final JdbcTemplate jdbcTemplate;
	
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = SequenceProperties.PREFIX, name = "backend", havingValue = "mysql")
	public SeqSynchronizer mysqlSynchronizer(SequenceProperties sequenceProperties) {
		MySqlSynchronizer synchronizer = new MySqlSynchronizer(sequenceProperties.getTableName(), jdbcTemplate);
		if (!sequenceProperties.isLazyInit()) {
			synchronizer.init();
		}
		return synchronizer;
	}
	
	
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = SequenceProperties.PREFIX, name = "backend", havingValue = "oracle")
	public SeqSynchronizer oracleSynchronizer(SequenceProperties sequenceProperties) {
		OracleSynchronizer synchronizer = new OracleSynchronizer(sequenceProperties.getTableName(), jdbcTemplate);
		if (!sequenceProperties.isLazyInit()) {
			synchronizer.init();
		}
		return synchronizer;
	}
}
