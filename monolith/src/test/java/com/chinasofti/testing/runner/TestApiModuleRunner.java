package com.chinasofti.testing.runner;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.chinasofti.core.tool.jackson.JsonUtil;
import com.chinasofti.testing.core.runner.ApiModuleRunner;
import com.chinasofti.testing.dto.HttpRequestElement;
import com.chinasofti.testing.dto.KeyValue;
import com.chinasofti.testing.dto.RunApiModuleRequest;
import com.chinasofti.testing.entity.ApiModule;
import com.chinasofti.testing.entity.Environment;
import com.chinasofti.testing.vo.ApiModuleRunResult;

public class TestApiModuleRunner {

	@Test
	public void testGetRequest()
	{
		Environment environment = new Environment();
		environment.setDomain( "http://127.0.0.1:12306" );
		ApiModuleRunner runner = ApiModuleRunner.getRunner(environment);
		RunApiModuleRequest apiModule = new RunApiModuleRequest();
		apiModule.setMethod( "get" );
		apiModule.setPath( "/kycReviewInfo" );
		HttpRequestElement ele = new HttpRequestElement();
		List<KeyValue> headers = new ArrayList<KeyValue>();
		KeyValue keyValue = new KeyValue();
		keyValue.setName( "token" );
		keyValue.setValue( "14085qswed" );
		headers.add( keyValue );
		ele.setHeaders( headers );
		
		List<KeyValue> querys = new ArrayList<KeyValue>();
		KeyValue query = new KeyValue();
		query.setName( "customerNumber" );
		query.setValue( "123456" );
		querys.add( query );
		ele.setArguments(querys);
		apiModule.setRequest( ele );
		ApiModuleRunResult  result = runner.run(apiModule);
		System.out.println( result.getStatusCode() + ":" + result.getBeginTime() + ":" + result.getTotalTime() );
	}
}
