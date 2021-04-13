package com.chinasofti.core.serialnumber.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = SequenceProperties.PREFIX)
public class SequenceProperties {

	public final static String PREFIX = "boot.sequence";

	/**
	 * 开关
	 */
	private boolean enabled = true;

	/**
	 * 后端类型
	 */
	private BackendTypeEnum backend;

	/**
	 * 懒加载
	 */
	private boolean lazyInit = false;

	/**
	 * 表名称(对于Redis则表示缓存名称,对于MongoDB则是集合名称，以此类推)
	 */
	private String tableName = "sys_seq_registry";

	/**
	 * 每次从后端取值的步进,这个值需要权衡性能和序号丢失
	 */
	private int fetchSize = 100;

	/**
	 * 序号名称
	 */
	private String name = "seq";

	/**
	 * 起始值
	 */
	private long startValue = 1L;

	/**
	 * Lettuce URI
	 * <a>https://lettuce.io/core/release/reference/index.html#redisuri.uri-syntax</a>
	 */
	private String lettuceUri = "redis://localhost";

	public enum BackendTypeEnum {

		/**
		 * MySQL
		 */
		mysql,
		/**
		 * Redis
		 */
		redis,
		/**
		 * Redis Cluster
		 */
		redisCluster,
		oracle
	}

}
