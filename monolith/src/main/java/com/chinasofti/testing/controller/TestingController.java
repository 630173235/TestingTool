	
package com.chinasofti.testing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinasofti.core.boot.ctrl.BootController;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.utils.StringUtil;
import com.chinasofti.testing.core.assertr.AssertClass;
import com.chinasofti.testing.core.definiton.AssertTemplate;
import com.chinasofti.testing.core.definiton.TestCase;
import com.chinasofti.testing.core.definiton.TestProject;
import com.chinasofti.testing.core.definiton.TestSuit;
import com.chinasofti.testing.core.props.RestTestProperties;
import com.chinasofti.testing.core.runner.TestCaseRunner;
import com.chinasofti.testing.core.runner.TestCaseRunner2;

/**
 * rest api测试
 *
 * @author Micro
 * @since 2020-12-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/rest-test")
@Api(value = "rest api测试", tags = "rest api测试")
@Slf4j
public class TestingController extends BootController {
	
	private final RestTestProperties restTestProperties;
	
	//private ITestlogService testlogService;
	
	private AssertClass assertClass;
	
	@PostMapping("/getAssertTempalte")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "提取断言模板", notes = "传入测试用例模板")
	public R<List<AssertTemplate>> getAssertTempalte() {
		List<AssertTemplate> assertTempaltes = assertClass.getAssertTemplates();
		return R.data(assertTempaltes);
	}
	
	@PostMapping("/run")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "测试用例执行V0.1", notes = "传入测试用例模板")
	public R<String> test(@RequestBody TestProject testProject) {
		String htmlFileName = TestCaseRunner.getRunner( restTestProperties.getReport().getPath() , testProject).run();
		String url = restTestProperties.getReport().getHost() + "/" + htmlFileName;
		saveLog( url , htmlFileName , testProject );
		return R.data(url);
	}

	@PostMapping("/runV02")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "测试用例执行V0.2", notes = "传入测试用例模板")
	public R<String> test2(@RequestBody TestProject testProject) {
		String htmlFileName = TestCaseRunner2.getRunner( restTestProperties.getReport().getPath() , testProject ,assertClass).run();
		String url = restTestProperties.getReport().getHost() + "/" + htmlFileName;
		saveLog( url , htmlFileName , testProject );
		return R.data(url);
	}
	
	private void saveLog( String reportUrl , String htmlFileName , TestProject testProject )
	{
		/*
		Testlog testlog = new Testlog();
		testlog.setIsDeleted( 0 );
		testlog.setProjectId( testProject.getId() );
		testlog.setRunDate( LocalDateTime.now() );
		testlog.setReportUrl( reportUrl );
		
		List<TestSuit> suits = testProject.getSuits();
		String testCaseName = "";
		String testCaseNumber = "";
		String apiName = "";
		
		for( TestSuit suit : suits )
		{
			List<TestCase> testCases = suit.getTestCases();
			for( TestCase testCase : testCases )
			{
				if( StringUtil.isNotBlank( testCase.getName() )  )
				    testCaseName = testCaseName + testCase.getName() + ",";
				if(  StringUtil.isNotBlank( testCase.getNumber() )  )
				    testCaseNumber = testCaseNumber + testCase.getNumber() + ",";
			}
			
			if( StringUtil.isNotBlank( suit.getName() ) )
			{
				apiName = apiName + suit.getName() + ",";
			}
		}
		if( StringUtil.isNotBlank( apiName.trim() ) )
	        testlog.setApiName( apiName.substring( 0 , apiName.length() - 1 ) );
		if( StringUtil.isNotBlank( testCaseName.trim() ) )
	        testlog.setCaseName( testCaseName.substring( 0 , testCaseName.length() - 1 ) );
		if( StringUtil.isNotBlank( testCaseNumber.trim() ) )
	        testlog.setCaseNumber( testCaseNumber.substring( 0 , testCaseNumber.length() - 1 ) );*/
		//testlogService.save(testlog);
	}
}
