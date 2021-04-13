
package com.chinasofti.core.swagger;

import com.chinasofti.core.launch.constant.AppConstant;
import com.chinasofti.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;
import java.util.Properties;

/**
 * 初始化Swagger配置
 *
 * @author Arvin Zhou
 */
//@AutoService(LauncherService.class)
public class SwaggerLauncherServiceImpl implements LauncherService {
	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile) {
		Properties props = System.getProperties();
		if (profile.equals(AppConstant.PROD_CODE)) {
			props.setProperty("knife4j.production", "true");
		}
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
