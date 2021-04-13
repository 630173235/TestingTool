package com.chinasofti.core.serialnumber.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;

import com.chinasofti.core.serialnumber.persistent.SeqSynchronizer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Endpoint(id = "sequence-synchronizer")
public class SeqSynchronizerEndpoint implements SmartInitializingSingleton {

	private final ApplicationContext applicationContext;

	private Map<String, SeqSynchronizer> synchronizerBeanMap;

	public SeqSynchronizerEndpoint(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterSingletonsInstantiated() {
		synchronizerBeanMap = applicationContext.getBeansOfType(SeqSynchronizer.class);
	}

	@ReadOperation
	public List<SynchronizerInfo> synchronizerInfo() {

		// @formatter:off

		return synchronizerBeanMap.entrySet()
				.stream()
				.map(kv -> new SynchronizerInfo(
						kv.getKey(),
						kv.getValue().getClass().getName(),
						kv.getValue().getQueryCounter(),
						kv.getValue().getUpdateCounter()))
				.collect(Collectors.toList());

		// @formatter:on
	}

	@Getter
	@AllArgsConstructor
	public static class SynchronizerInfo {

		private final String beanName;

		private final String className;

		private Long queryCount;

		private Long updateCount;

	}

}
