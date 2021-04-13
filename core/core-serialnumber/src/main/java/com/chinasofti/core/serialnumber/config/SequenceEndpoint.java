package com.chinasofti.core.serialnumber.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.chinasofti.core.serialnumber.Sequence;
import com.chinasofti.core.serialnumber.persistent.SeqHolder;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

@Endpoint(id = "sequence")
@Component
@Slf4j
public class SequenceEndpoint implements SmartInitializingSingleton  {

	private final ApplicationContext applicationContext;

	private List<SequenceInfo> sequenceInfoList;

	public SequenceEndpoint(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterSingletonsInstantiated() {
		sequenceInfoList = applicationContext.getBeansOfType(Sequence.class).entrySet().stream()
				.map(kv -> new SequenceInfo(kv.getKey(), kv.getValue().getClass().getName(), kv.getValue().getName()))
				.collect(Collectors.toList());
	}

	@ReadOperation
	public List<SequenceInfo> sequenceBeans() {
		return sequenceInfoList;
	}

	@PreDestroy
	public void destroy() throws Exception {
		  
		for( Sequence sequence :applicationContext.getBeansOfType(Sequence.class).values() )
		{
			  try
			  {
				  SeqHolder seqHolder = (SeqHolder) sequence;
				  boolean result = seqHolder.serializeCurrent();
				  if( result )
					  log.info( seqHolder.getName() + " backup success");
				  else
					  log.warn( seqHolder.getName() + " backup failure");
			  }
			  catch( Exception e )
			  {
				  e.printStackTrace();
			  }
		}  
	}
	
	@Getter
	@AllArgsConstructor
	public static class SequenceInfo {

		private final String beanName;

		private final String className;

		private final String seqName;

	}

}
