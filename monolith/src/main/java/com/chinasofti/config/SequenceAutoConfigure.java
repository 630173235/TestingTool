package com.chinasofti.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.chinasofti.core.serialnumber.SeqFormatter;
import com.chinasofti.core.serialnumber.Sequence;
import com.chinasofti.core.serialnumber.config.JdbcSynchronizerConfigure;
import com.chinasofti.core.serialnumber.config.RedisSynchronizerConfigure;
import com.chinasofti.core.serialnumber.config.SequenceProperties;
import com.chinasofti.core.serialnumber.persistent.Partitions;
import com.chinasofti.core.serialnumber.persistent.SeqHolder;
import com.chinasofti.core.serialnumber.persistent.SeqSynchronizer;

/**
 * 自动配置
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SequenceProperties.class)
@Import({ JdbcSynchronizerConfigure.class, RedisSynchronizerConfigure.class })
public class SequenceAutoConfigure {

	@Bean( "camcSequence")
	@ConditionalOnMissingBean(value = Long.class, parameterizedContainer = Sequence.class)
	@ConditionalOnProperty(prefix = SequenceProperties.PREFIX, name = "enabled", havingValue = "true",
			matchIfMissing = true)
	public Sequence<Long> sequence(SequenceProperties sequenceProperties, SeqSynchronizer seqSynchronizer) {
		log.info("CamcSequence create,Using {}", seqSynchronizer.getClass().getSimpleName());
		SeqHolder holder = new SeqHolder(seqSynchronizer, sequenceProperties.getName(), Partitions.CAMC,
				sequenceProperties.getStartValue(), sequenceProperties.getFetchSize(), SeqFormatter.DEFAULT_FORMAT);
		return holder;
	}

}
