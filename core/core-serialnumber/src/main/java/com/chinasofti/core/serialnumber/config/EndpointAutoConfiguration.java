package com.chinasofti.core.serialnumber.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class EndpointAutoConfiguration {

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	@ConditionalOnMissingBean(SequenceEndpoint.class)
	@ConditionalOnAvailableEndpoint(endpoint = SequenceEndpoint.class)
	public SequenceEndpoint sequenceEndpoint() {
		return new SequenceEndpoint(applicationContext);
	}

	@Bean
	@ConditionalOnMissingBean(SeqSynchronizerEndpoint.class)
	@ConditionalOnAvailableEndpoint(endpoint = SeqSynchronizerEndpoint.class)
	public SeqSynchronizerEndpoint seqSynchronizerEndpoint() {
		return new SeqSynchronizerEndpoint(applicationContext);
	}

}
