package com.chinasofti.core.serialnumber.persistent.provider;

import io.lettuce.core.api.sync.RedisKeyCommands;
import io.lettuce.core.api.sync.RedisScriptingCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.chinasofti.core.serialnumber.exception.SeqException;
import com.chinasofti.core.serialnumber.persistent.SeqSynchronizer;

import java.util.function.Function;

/**
 * Lettuce 集群支持
 */
public class LettuceClusterSynchronizer extends AbstractLettuceSynchronizer implements SeqSynchronizer {

	private final GenericObjectPool<StatefulRedisClusterConnection<String, String>> pool;

	public LettuceClusterSynchronizer(String cacheName, RedisClusterClient redisClient) {
		this(cacheName, redisClient, new GenericObjectPoolConfig());
	}

	public LettuceClusterSynchronizer(String cacheName, RedisClusterClient redisClient,
			GenericObjectPoolConfig<StatefulRedisClusterConnection<String, String>> poolConfig) {
		super(cacheName);
		this.pool = ConnectionPoolSupport.createGenericObjectPool(() -> redisClient.connect(), poolConfig);
	}

	public LettuceClusterSynchronizer(String cacheName,
			GenericObjectPool<StatefulRedisClusterConnection<String, String>> pool) {
		super(cacheName);
		this.pool = pool;
	}

	public <R> R execCommand(Function<RedisAdvancedClusterCommands<String, String>, R> func) {
		try (StatefulRedisClusterConnection<String, String> connection = getRedisConnection()) {
			return func.apply(connection.sync());
		}
	}

	protected StatefulRedisClusterConnection<String, String> getRedisConnection() {
		try {
			return pool.borrowObject();
		}
		catch (Exception e) {
			throw new SeqException(e.getMessage(), e);
		}
	}

	@Override
	public void shutdown() {
		pool.close();
	}

	@Override
	protected <R> R execStringCommand(Function<RedisStringCommands<String, String>, R> func) {
		try (StatefulRedisClusterConnection<String, String> connection = getRedisConnection()) {
			return func.apply(connection.sync());
		}
	}

	@Override
	protected <R> R execScriptingCommand(Function<RedisScriptingCommands<String, String>, R> func) {
		try (StatefulRedisClusterConnection<String, String> connection = getRedisConnection()) {
			return func.apply(connection.sync());
		}
	}

	@Override
	protected <R> R execKeyCommand(Function<RedisKeyCommands<String, String>, R> func) {
		try (StatefulRedisClusterConnection<String, String> connection = getRedisConnection()) {
			return func.apply(connection.sync());
		}
	}

}
