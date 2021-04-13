package com.chinasofti.core.serialnumber.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chinasofti.core.serialnumber.persistent.SeqSynchronizer;
import com.chinasofti.core.serialnumber.persistent.provider.LettuceClusterSynchronizer;
import com.chinasofti.core.serialnumber.persistent.provider.SimpleLettuceSynchronizer;

@Configuration
@ConditionalOnClass({ RedisClient.class, RedisClusterClient.class })
public class RedisSynchronizerConfigure {

	@Bean(destroyMethod = "shutdown")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = SequenceProperties.PREFIX, name = "backend", havingValue = "redis")
	public RedisClient redisClient(SequenceProperties sequenceProperties) {
		RedisURI redisUri = RedisURI.create(sequenceProperties.getLettuceUri());
		return RedisClient.create(redisUri);
	}

	@Bean(destroyMethod = "shutdown")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = SequenceProperties.PREFIX, name = "backend", havingValue = "redis-cluster")
	public RedisClusterClient redisClusterClient(SequenceProperties sequenceProperties) {
		RedisURI redisUri = RedisURI.create(sequenceProperties.getLettuceUri());
		return RedisClusterClient.create(redisUri);
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(RedisClient.class)
	public SeqSynchronizer redisSynchronizer(SequenceProperties sequenceProperties, RedisClient redisClient) {
		SimpleLettuceSynchronizer synchronizer = new SimpleLettuceSynchronizer(sequenceProperties.getTableName(),
				redisClient);
		if (!sequenceProperties.isLazyInit()) {
			synchronizer.init();
		}
		return synchronizer;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(RedisClusterClient.class)
	public SeqSynchronizer redisClusterSynchronizer(SequenceProperties sequenceProperties,
			RedisClusterClient redisClusterClient) {
		LettuceClusterSynchronizer synchronizer = new LettuceClusterSynchronizer(sequenceProperties.getTableName(),
				redisClusterClient);
		if (!sequenceProperties.isLazyInit()) {
			synchronizer.init();
		}
		return synchronizer;
	}

}
