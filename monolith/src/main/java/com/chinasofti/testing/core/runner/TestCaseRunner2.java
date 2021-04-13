package com.chinasofti.testing.core.runner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinasofti.core.tool.jackson.JsonUtil;
import com.chinasofti.core.tool.utils.IoUtil;
import com.chinasofti.core.tool.utils.StringUtil;
import com.chinasofti.testing.core.assertr.AssertClass;
import com.chinasofti.testing.core.definiton.TestAssert;
import com.chinasofti.testing.core.definiton.TestCase;
import com.chinasofti.testing.core.definiton.TestCaseResult;
import com.chinasofti.testing.core.definiton.TestProject;
import com.chinasofti.testing.core.definiton.TestResult;
import com.chinasofti.testing.core.definiton.TestStep;
import com.chinasofti.testing.core.definiton.TestSuit;
import com.chinasofti.testing.core.utils.HttpUtils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.extra.spring.SpringUtil;
import okhttp3.Response;

public class TestCaseRunner2 {

	protected final static Logger logger = LoggerFactory.getLogger(TestCaseRunner2.class);
	
	private HttpUtils httpUtils;
	
	private TestProject testProject;
	
	private AssertClass assertClass; 
	
	/*
	 * 如下是用例执行情况统计需要的数据结构，后续计划通过观察者模式重构，暂时先放在这里
	 */
    private TestResult testResult;
    private List<TestCaseResult> testCaseResultList;

    private String testBeginTime;

    // 测试多个类的时候，计第一个测试类开始测试的时间
    private Long testBeginTimeMills;
    
    private String reportDirectory ; 
	/*
	 * --------------------------------------------------------------
	 */
	private TestCaseRunner2()
	{
		
	}
	
	private TestCaseRunner2(HttpUtils _httpUtils, TestProject project , String reportDirectory , AssertClass assertClass ) {
		// TODO Auto-generated constructor stub
		this.httpUtils = _httpUtils;
		this.testProject = project;
		testResult = new TestResult();
        testCaseResultList = new ArrayList<>();
        this.reportDirectory = reportDirectory;
        if( !reportDirectory.endsWith( "/" ) )
        	this.reportDirectory = this.reportDirectory + "/";
        if( assertClass == null )
            this.assertClass = SpringUtil.getBean(AssertClass.class); 
        else
        	this.assertClass = assertClass;
	}
	
	public void serAssertClass( AssertClass assertClass )
	{
		this.assertClass = assertClass;
	}
	
	public static TestCaseRunner2 getRunner( String reportdirectory , TestProject project , AssertClass assertClass )
	{
		HttpUtils _httpUtils = new HttpUtils( project.getBaseUrl() );
		TestCaseRunner2 testCaseRunner = new TestCaseRunner2( _httpUtils , project , reportdirectory , assertClass );
		return testCaseRunner;
	}
	
	public static TestCaseRunner2 getRunner( String reportdirectory , TestProject project )
	{
		HttpUtils _httpUtils = new HttpUtils( project.getBaseUrl() );
		TestCaseRunner2 testCaseRunner = new TestCaseRunner2( _httpUtils , project , reportdirectory , null );
		return testCaseRunner;
	}
	
	public String run() {
		
		/*
		 * 执行情况统计，后续重构
		 */
		testBeginTime = new Date().toString();
        testBeginTimeMills = System.currentTimeMillis();
        boolean wasSuccessful=true;
        Integer caseCount = 0;
        Integer failureCount = 0;
        Integer ignoreCount = 0;
		/*
		 * ----------------------
		 */
		
		
		List<TestSuit> suits = testProject.getSuits();
		for( TestSuit suit : suits )
		{
			
			
			List<TestCase> testCases = suit.getTestCases();
			logger.debug( "testCases.size :" +testCases.size() );
			caseCount = caseCount + testCases.size();
			for( TestCase testCase : testCases )
			{
				TestStep testStep = new TestStep();
				testStep.appendCookies( testProject.getCookies() );
				testStep.appendCookies( suit.getCookies() );
				testStep.appendHeaders( testProject.getHeaders() );
				testStep.appendHeaders( suit.getHeaders() );
				testStep.setPath( suit.getPath() );
				
				//---------------
				Long testCaseBeginTime = System.currentTimeMillis();
				TestCaseResult testCaseResult = new TestCaseResult();
				List<String> throwableLog = new ArrayList<String>();
				String description = "";
				//-----------------
				
				testStep.appendCookies( testCase.getCookies() );
				testStep.appendHeaders( testCase.getHeaders() );
				testStep.setBody( testCase.getBody() );
				testStep.setParams( testCase.getParams() );
				testStep.setType( testCase.getMethodType() );
				if( StringUtil.isBlank( testStep.getPath() ) )
					testStep.setPath( testCase.getPath() );
				if( !testCase.getPathVariables().isEmpty() )
				{
					String path = testStep.getPath();
					for( String pathVariable : testCase.getPathVariables() )
					{
						path = path + "/" + pathVariable;
					}
					testStep.setPath( path );
				}
				try
				{
					Response response = httpUtils.request(testStep);
					testCaseResult.setClassName( testProject.getBaseUrl() + testStep.getPath());
			        testCaseResult.setMethodName(testCase.getName());
					List<TestAssert> asserts = testCase.getAsserts();
					AssertClass.AssertResult assertResult = this.assertClass.execute(asserts , response);
					
					if(assertResult.result == Boolean.TRUE )
					{
						testCaseResult.setStatus("success");
					}
					else
					{
						testCaseResult.setStatus("failure");
						failureCount++;
						wasSuccessful = false;
					}
					
					description = assertResult.desc.toString();
				}
				catch( Throwable failure )
				{
					failure.printStackTrace();
					//---------------------------
					wasSuccessful = false;
					testCaseResult.setStatus("failure");
					throwableLog.add(failure.toString());
					description =  failure.toString();
			        StackTraceElement[] st = failure.getStackTrace();
			        for (StackTraceElement stackTraceElement : st) {
			                throwableLog.add("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + stackTraceElement);
			        }
			        failureCount++;
                    //-----------------------------
				}
				finally
				{
					testCaseResult.setSpendTime(System.currentTimeMillis() - testCaseBeginTime + "ms");
					testCaseResult.setThrowableLog(throwableLog);
					testCaseResult.setDescription(description);
					testCaseResultList.add(testCaseResult);
				}
			}
		}
		
		//--------------------
		 Long totalTimes = System.currentTimeMillis() - testBeginTimeMills;

	     logger.info("执行结果 : {}", wasSuccessful);
	     logger.info("执行时间 : {}", totalTimes);
	     logger.info("用例总数 : {}", caseCount );
	     logger.info("失败数量 : {}", failureCount);
	     logger.info("忽略数量 : {}", ignoreCount);

	     testResult.setTestName( testProject.getName() );

	     testResult.setTestAll(caseCount);
	     testResult.setTestPass(caseCount - failureCount);
	     testResult.setTestFail(failureCount);
	     testResult.setTestSkip(ignoreCount);

	     testResult.setTestResult(testCaseResultList);

	     testResult.setBeginTime(testBeginTime);
	     testResult.setTotalTime(totalTimes + "ms");
	     String htmlFileName = testProject.getCode() + "/" + UUID.randomUUID().toString(true) + ".html";
	     createReport(htmlFileName) ;
	     return htmlFileName;
	      //-------------
	}
	
	private void createReport( String html) {

		  InputStream inputStream = null;
		  try
		  {
			  inputStream = this.getClass().getClassLoader().getResourceAsStream("template.html");
		      String template = IoUtil.toString(inputStream);
		      String jsonString = JsonUtil.toJson(testResult);
		      template = template.replace("${resultData}", jsonString);
		      logger.debug( "report -> " +  this.reportDirectory + html );
		      FileUtil.writeString(template, new File( this.reportDirectory + html), "UTF-8");
		  }
		  catch( Throwable e )
		  {
			  e.printStackTrace();
		  }
	      finally
	      {
	    	  if( inputStream != null )
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      }
	}	
}
