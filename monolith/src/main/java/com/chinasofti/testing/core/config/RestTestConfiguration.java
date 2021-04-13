package com.chinasofti.testing.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chinasofti.testing.core.props.RestTestProperties;


@Configuration
@EnableConfigurationProperties(RestTestProperties.class)
public class RestTestConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public RestTestProperties restTestProperties() {
		return new RestTestProperties();
	}
	
}
