
package com.chinasofti.common.launch;

import com.chinasofti.common.constant.CommonConstant;
import com.chinasofti.common.constant.LauncherConstant;
import com.chinasofti.core.launch.service.LauncherService;
import com.chinasofti.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author Arvin Zhou
 */
//@AutoService(LauncherService.class)
@Slf4j
public class LauncherServiceImpl implements LauncherService {

	
	public void launcher(SpringApplicationBuilder builder, String appName, String profile) {
		Properties props = System.getProperties();
		//PropsUtil.setProperty(props, "spring.cloud.sentinel.transport.dashboard", LauncherConstant.sentinelAddr(profile));
		PropsUtil.setProperty(props, "spring.datasource.dynamic.enabled", "false");
	}

}
