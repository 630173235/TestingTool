package com.chinasofti.testing.core.runner;


import com.chinasofti.core.tool.utils.StringUtil;
import com.chinasofti.testing.core.definiton.TestStep;
import com.chinasofti.testing.core.enums.ContentType;
import com.chinasofti.testing.core.enums.HttpType;
import com.chinasofti.testing.core.utils.HttpRequestElementUtil;
import com.chinasofti.testing.core.utils.HttpUtils;
import com.chinasofti.testing.dto.HttpRequestElement;
import com.chinasofti.testing.dto.RunApiModuleRequest;
import com.chinasofti.testing.entity.Environment;
import com.chinasofti.testing.entity.Project;
import com.chinasofti.testing.vo.ApiModuleRunResult;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApiModuleRunner {

	protected final static Logger logger = LoggerFactory.getLogger(ApiModuleRunner.class);
	
	private HttpUtils httpUtils;
	
	private Environment environment;
	
	private Project project; 

    private String testBeginTime;

    // 测试多个类的时候，计第一个测试类开始测试的时间
    private Long testBeginTimeMills;
    
	/*
	 * --------------------------------------------------------------
	 */
	private ApiModuleRunner()
	{
		
	}
	
	private ApiModuleRunner(HttpUtils _httpUtils, Project project ) {
		// TODO Auto-generated constructor stub
		this.httpUtils = _httpUtils;
		this.project = project;
	}
	
	public static ApiModuleRunner getRunner( Project project ,Environment environment )
	{
		HttpUtils _httpUtils = new HttpUtils( environment.getDomain() );
		ApiModuleRunner testCaseRunner = new ApiModuleRunner( _httpUtils , project  );
		return testCaseRunner;
	}
	
	public static ApiModuleRunner getRunner( Environment environment )
	{
		HttpUtils _httpUtils = new HttpUtils( environment.getDomain() );
		ApiModuleRunner testCaseRunner = new ApiModuleRunner( _httpUtils , null );
		return testCaseRunner;
	}
	
	public ApiModuleRunResult run(RunApiModuleRequest runApiModuleRequest) {
		testBeginTime = new Date().toString();
        testBeginTimeMills = System.currentTimeMillis();

        ApiModuleRunResult runResult = new ApiModuleRunResult();
		TestStep testStep = new TestStep();
		HttpRequestElement request = runApiModuleRequest.getRequest();
		testStep.appendHeaders( HttpRequestElementUtil.toHeaderMap(request)  );
		testStep.setPath( runApiModuleRequest.getPath() );	
		List<String> throwableLog = new ArrayList<String>();
		testStep.setType (HttpRequestElementUtil.toHttpType( runApiModuleRequest.getMethod() ));
		if(testStep.getType() != HttpType.GET && request.getBody() != null )
		    testStep.setBody(request.getBody().getRaw());
		if( runApiModuleRequest.getMethod().equals( "post" ) )
		    testStep.setContentType( ContentType.JSON );
		if( runApiModuleRequest.getMethod().equals( "put" ) )
			testStep.setContentType( ContentType.JSON );
		testStep.setParams( HttpRequestElementUtil.toQueryParams(request)  );
		if( StringUtil.isBlank( testStep.getPath() ) )
			testStep.setPath( runApiModuleRequest.getPath() );
		List<String> pathVariables = HttpRequestElementUtil.toRestParams(request)  ;
		if( !pathVariables.isEmpty() )
		{
			String path = testStep.getPath();
			for( String pathVariable : pathVariables )
			{
				path = path + "/" + pathVariable;
			}
			testStep.setPath( path );
		}
		try
		{
			Response response = httpUtils.request(testStep);
			runResult.setStatusCode( response.code() );
		}
		catch( Throwable failure )
		{
			failure.printStackTrace();
			//---------------------------
			throwableLog.add(failure.toString());
	        StackTraceElement[] st = failure.getStackTrace();
	        for (StackTraceElement stackTraceElement : st) {
	                throwableLog.add("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + stackTraceElement);
	        }
            //-----------------------------
		}
		finally
		{
			runResult.setThrowableLog(throwableLog);
		}
		//--------------------
		 Long totalTimes = System.currentTimeMillis() - testBeginTimeMills;
	     logger.info("执行时间 : {}", totalTimes);
	     runResult.setBeginTime(testBeginTime);
	     runResult.setTotalTime(totalTimes + "ms");
	     return runResult;
	     //-------------
	}
}
