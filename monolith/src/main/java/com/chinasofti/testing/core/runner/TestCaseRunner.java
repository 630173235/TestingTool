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
import com.chinasofti.testing.core.definiton.TestCase;
import com.chinasofti.testing.core.definiton.TestCaseResult;
import com.chinasofti.testing.core.definiton.TestExpectResult;
import com.chinasofti.testing.core.definiton.TestProject;
import com.chinasofti.testing.core.definiton.TestResult;
import com.chinasofti.testing.core.definiton.TestStep;
import com.chinasofti.testing.core.definiton.TestSuit;
import com.chinasofti.testing.core.enums.ExpectResultType;
import com.chinasofti.testing.core.enums.HttpType;
import com.chinasofti.testing.core.utils.HttpUtils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import okhttp3.Response;

public class TestCaseRunner {

	protected final static Logger logger = LoggerFactory.getLogger(TestCaseRunner.class);
	
	private HttpUtils httpUtils;
	
	private TestProject testProject;
	
	
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
	private TestCaseRunner()
	{
		
	}
	
	private TestCaseRunner(HttpUtils _httpUtils, TestProject project , String reportDirectory ) {
		// TODO Auto-generated constructor stub
		this.httpUtils = _httpUtils;
		this.testProject = project;
		testResult = new TestResult();
        testCaseResultList = new ArrayList<>();
        this.reportDirectory = reportDirectory;
        if( !reportDirectory.endsWith( "/" ) )
        	this.reportDirectory = this.reportDirectory + "/";
        
	}
	
	public static TestCaseRunner getRunner( String reportdirectory , TestProject project )
	{
		HttpUtils _httpUtils = new HttpUtils( project.getBaseUrl() );
		TestCaseRunner testCaseRunner = new TestCaseRunner( _httpUtils , project , reportdirectory );
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
			TestStep testStep = new TestStep();
			testStep.appendCookies( testProject.getCookies() );
			testStep.appendCookies( suit.getCookies() );
			testStep.appendHeaders( testProject.getHeaders() );
			testStep.appendHeaders( suit.getHeaders() );
			testStep.setPath( suit.getPath() );
			
			List<TestCase> testCases = suit.getTestCases();
			caseCount = caseCount + testCases.size();
			for( TestCase testCase : testCases )
			{
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
					List<TestExpectResult> expectResults = testCase.getExpectResults();
					for( TestExpectResult testExpectResult : expectResults )
					{
						testCaseResult.setClassName(testCase.getName());
				        testCaseResult.setMethodName(testCase.getName());
				        
				        
						if( ExpectResultType.STATUS == testExpectResult.getType() )
						{
							//-------------
							logger.debug( "response.getStatusCode() = " + response.code() + ", " + response.body().string() );
							if( response.code() == Integer.parseInt( testExpectResult.getContent() ) )
							{
								testCaseResult.setStatus("成功");
							}
							else
							{
								testCaseResult.setStatus("失败");
								failureCount++;
								wasSuccessful = false;
							}
							
							description = "预期 " + ExpectResultType.STATUS.toString() + "=" + Integer.parseInt( testExpectResult.getContent() ) 
								+ ",实际 " + ExpectResultType.STATUS.toString() + "=" + response.code() ;
							//-----------------
							//testCaseResultList.add(testCaseResult);
						}
						else
						{
							logger.info( "断言还未实现,此处按忽略统计" );
							testCaseResult.setStatus("忽略");
							throwableLog.add( "断言还未实现,此处按忽略统计" );
							ignoreCount++;
							
						}
					}
				}
				catch( Throwable failure )
				{
					failure.printStackTrace();
					//---------------------------
					wasSuccessful = false;
					testCaseResult.setStatus("失败");
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
	
	public static void main( String args[] )
	{		
		TestProject testProject = new TestProject();
		testProject.setName( "用于API测试的服务" );
		testProject.setCode( "001" );
		testProject.setBaseUrl( "http://192.168.1.103:12306" );        
		testProject.appendHeader("code", "123456" );
		testProject.appendHeader( "token" , "2ae3e7aeaf4642a4b6b1914d857b235d" );
		testProject.setDescription( "用于API测试的服务" );
		
		TestSuit suit = new TestSuit();
		//suit.setName( "豆瓣电影 top 250" );
		//suit.setDescription( "豆瓣电影 top 250" );
		suit.setPath( "/top250" );
		TestCase testCase = new TestCase();
		testCase.setName( "错误的访问Key测试" );
		testCase.setMethodType(HttpType.GET );
		testCase.appendParams( "start", 1 );
		testCase.appendParams( "count", 2 );
		TestExpectResult testExpectResult = new TestExpectResult();
		testExpectResult.setType(ExpectResultType.STATUS);
		testExpectResult.setContent( "403" );
		testCase.appendExpectResult(testExpectResult);
		suit.appendTestCase( testCase );
		testProject.appendTestSuit(suit);
		
		TestSuit suit2 = new TestSuit();
		suit2.setName( "影人条目信息" );
		suit2.setDescription( "影人条目信息" );
		suit2.setPath( "/celebrity" );
		TestCase testCase2 = new TestCase();
		testCase2.setName( "错误的访问Key测试" );
		testCase2.setMethodType(HttpType.GET );
		testCase2.appendPathVariable( "1031931" );
		TestExpectResult testExpectResult2 = new TestExpectResult();
		testExpectResult2.setType(ExpectResultType.STATUS);
		testExpectResult2.setContent( "403" );
		testCase2.appendExpectResult(testExpectResult2);
		suit2.appendTestCase( testCase2 );
		testProject.appendTestSuit(suit2);
		TestCaseRunner.getRunner( "D:/reports" , testProject ).run();
		
	}
}
