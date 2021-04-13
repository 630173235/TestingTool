package com.chinasofti.core.datascope.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 数据权限参数配置类
 */
@Data
public class DataScopeProperties {

	/**
	 * 开启数据权限
	 */
	private Boolean enabled = false;
	/**
	 * mapper方法匹配关键字
	 */
	private List<String> mapperKey = Arrays.asList("page", "Page", "list", "List");

	/**
	 * mapper过滤
	 */
	private List<String> mapperExclude = Collections.singletonList("FlowMapper");

}
