
package com.chinasofti.core.launch.constant;

/**
 * 系统常量
 *
 * @author Arvin Zhou
 */
public interface AppConstant {

	/**
	 * 应用信息配置文件名
	 */
	String APPLICATION_INFO_FILE = "application.info.txt";
	
	/**
	 * 应用信息配置版本key
	 */
	String APPLICATION_INFO_VERSION_KEY = "app.version";
	
	/**
	 * 应用信息配置 应用名key
	 */
	String APPLICATION_INFO_NAME_KEY = "app.name";
	
	/**
	 * 应用版本
	 */
	String APPLICATION_VERSION = "hardcode";

	/**
	 * 基础包
	 */
	String BASE_PACKAGES = "com.chinasofti";
	
	/**
	 * 应用名前缀
	 */
	String APPLICATION_NAME_PREFIX = "micro-";
	/**
	 * 网关模块名称
	 */
	String APPLICATION_GATEWAY_NAME = APPLICATION_NAME_PREFIX + "gateway";
	/**
	 * 授权模块名称
	 */
	String APPLICATION_AUTH_NAME = APPLICATION_NAME_PREFIX + "auth";
	/**
	 * 监控模块名称
	 */
	String APPLICATION_ADMIN_NAME = APPLICATION_NAME_PREFIX + "admin";
	/**
	 * 首页模块名称
	 */
	String APPLICATION_DESK_NAME = APPLICATION_NAME_PREFIX + "desk";
	/**
	 * 系统模块名称
	 */
	String APPLICATION_SYSTEM_NAME = APPLICATION_NAME_PREFIX + "system";
	/**
	 * 用户模块名称
	 */
	String APPLICATION_USER_NAME = APPLICATION_NAME_PREFIX + "user";
	/**
	 * 日志模块名称
	 */
	String APPLICATION_LOG_NAME = APPLICATION_NAME_PREFIX + "log";
	/**
	 * 开发模块名称
	 */
	String APPLICATION_DEVELOP_NAME = APPLICATION_NAME_PREFIX + "develop";
	/**
	 * 资源模块名称
	 */
	String APPLICATION_RESOURCE_NAME = APPLICATION_NAME_PREFIX + "resource";
	/**
	 * 链路追踪模块名称
	 */
	String APPLICATION_ZIPKIN_NAME = APPLICATION_NAME_PREFIX + "zipkin";
	/**
	 * 报表系统名称
	 */
	String APPLICATION_REPORT_NAME = APPLICATION_NAME_PREFIX + "report";
	/**
	 * 测试模块名称
	 */
	String APPLICATION_TEST_NAME = APPLICATION_NAME_PREFIX + "test";

	/**
	 * API管理模块名称
	 */
	String APPLICATION_API_NAME = APPLICATION_NAME_PREFIX + "api";

	/**
	 * 开发环境
	 */
	String DEV_CODE = "dev";
	/**
	 * 生产环境
	 */
	String PROD_CODE = "prod";
	/**
	 * 测试环境
	 */
	String TEST_CODE = "test";

	/**
	 * 代码部署于 linux 上，工作默认为 mac 和 Windows
	 */
	String OS_NAME_LINUX = "LINUX";

}
