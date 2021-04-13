package com.chinasofti.core.serialnumber.persistent.provider;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.api.sync.RedisKeyCommands;
import io.lettuce.core.api.sync.RedisScriptingCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.chinasofti.core.serialnumber.exception.SeqException;
import com.chinasofti.core.serialnumber.persistent.SeqSynchronizer;

import java.util.function.Function;

/**
 * 普通Lettuce连接池同步
 */
public class SimpleLettuceSynchronizer extends AbstractLettuceSynchronizer implements SeqSynchronizer {

	private final GenericObjectPool<StatefulRedisConnection<String, String>> pool;

	public SimpleLettuceSynchronizer(String cacheName, RedisClient redisClient) {
		this(cacheName, redisClient, new GenericObjectPoolConfig());
	}

	public SimpleLettuceSynchronizer(String cacheName, RedisClient redisClient,
			GenericObjectPoolConfig<StatefulRedisConnection<String, String>> poolConfig) {
		super(cacheName);
		this.pool = ConnectionPoolSupport.createGenericObjectPool(() -> redisClient.connect(), poolConfig);
	}

	public SimpleLettuceSynchronizer(String cacheName,
			GenericObjectPool<StatefulRedisConnection<String, String>> pool) {
		super(cacheName);
		this.pool = pool;
	}

	public <R> R execCommand(Function<RedisCommands<String, String>, R> func) {
		try (StatefulRedisConnection<String, String> connection = getRedisConnection()) {
			return func.apply(connection.sync());
		}
	}

	protected StatefulRedisConnection<String, String> getRedisConnection() {
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
		try (StatefulRedisConnection<String, String> connection = getRedisConnection()) {
			return func.apply(connection.sync());
		}
	}

	@Override
	protected <R> R execScriptingCommand(Function<RedisScriptingCommands<String, String>, R> func) {
		try (StatefulRedisConnection<String, String> connection = getRedisConnection()) {
			return func.apply(connection.sync());
		}
	}

	@Override
	protected <R> R execKeyCommand(Function<RedisKeyCommands<String, String>, R> func) {
		try (StatefulRedisConnection<String, String> connection = getRedisConnection()) {
			return func.apply(connection.sync());
		}
	}

}
