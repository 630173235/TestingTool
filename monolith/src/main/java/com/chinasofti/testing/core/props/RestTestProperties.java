package com.chinasofti.testing.core.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 *
 * @author Arvin Zhou
 */
@Data
@ConfigurationProperties(prefix = "rest-test")
public class RestTestProperties {

	private Assert testAssert;
	
	/**
	 * 报告
	 */
	private Report report;

}
