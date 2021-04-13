package com.chinasofti.core.test;


import org.junit.runners.model.InitializationError;
import com.chinasofti.core.launch.BootApplication;
import com.chinasofti.core.launch.constant.AppConstant;
import com.chinasofti.core.launch.constant.NacosConstant;
import com.chinasofti.core.launch.constant.SentinelConstant;
import com.chinasofti.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 设置启动参数
 *
 */
public class BootSpringRunner extends SpringJUnit4ClassRunner{

	public BootSpringRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
		setUpTestClass(clazz);
	}

	private void setUpTestClass(Class<?> clazz) {
		BootTest bladeBootTest = AnnotationUtils.getAnnotation(clazz, BootTest.class);
		if (bladeBootTest == null) {
			throw new BootTestException(String.format("%s must be @BootTest .", clazz));
		}
		String appName = bladeBootTest.appName();
		String profile = bladeBootTest.profile();
		boolean isLocalDev = BootApplication.isLocalDev();
		Properties props = System.getProperties();
		props.setProperty("boot.env", profile);
		props.setProperty("boot.name", appName);
		props.setProperty("boot.is-local", String.valueOf(isLocalDev));
		props.setProperty("boot.dev-mode", profile.equals(AppConstant.PROD_CODE) ? "false" : "true");
		props.setProperty("boot.service.version", AppConstant.APPLICATION_VERSION);
		props.setProperty("spring.application.name", appName);
		props.setProperty("spring.profiles.active", profile);
		props.setProperty("info.version", AppConstant.APPLICATION_VERSION);
		props.setProperty("info.desc", appName);
		//props.setProperty("spring.cloud.nacos.discovery.server-addr", NacosConstant.NACOS_ADDR);
		//props.setProperty("spring.cloud.nacos.config.server-addr", NacosConstant.NACOS_ADDR);
		//props.setProperty("spring.cloud.nacos.config.prefix", NacosConstant.NACOS_CONFIG_PREFIX);
		//props.setProperty("spring.cloud.nacos.config.file-extension", NacosConstant.NACOS_CONFIG_FORMAT);
		//props.setProperty("spring.cloud.sentinel.transport.dashboard", SentinelConstant.SENTINEL_ADDR);
		props.setProperty("spring.main.allow-bean-definition-overriding", "true");
		// 加载自定义组件
		if (bladeBootTest.enableLoader()) {
			List<LauncherService> launcherList = new ArrayList<>();
			SpringApplicationBuilder builder = new SpringApplicationBuilder(clazz);
			ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
			launcherList.stream().sorted(Comparator.comparing(LauncherService::getOrder)).collect(Collectors.toList())
				.forEach(launcherService -> launcherService.launcher(builder, appName, profile));
		}
		System.err.println(String.format("---[junit.test]:[%s]---启动中，读取到的环境变量:[%s]", appName, profile));
	}

}
