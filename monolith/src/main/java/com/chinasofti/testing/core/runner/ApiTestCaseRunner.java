package com.chinasofti.testing.core.runner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinasofti.testing.core.enums.ContentType;
import com.chinasofti.testing.core.enums.HttpType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinasofti.core.secure.BootUser;
import com.chinasofti.core.tool.jackson.JsonUtil;
import com.chinasofti.core.tool.utils.IoUtil;
import com.chinasofti.core.tool.utils.StringUtil;
import com.chinasofti.system.user.service.IUserService;
import com.chinasofti.testing.core.assertr.AssertClass;
import com.chinasofti.testing.core.definiton.TestAssert;
import com.chinasofti.testing.core.definiton.TestCase;
import com.chinasofti.testing.core.definiton.TestCaseResult;
import com.chinasofti.testing.core.definiton.TestProject;
import com.chinasofti.testing.core.definiton.TestResult;
import com.chinasofti.testing.core.definiton.TestStep;
import com.chinasofti.testing.core.definiton.TestSuit;
import com.chinasofti.testing.core.props.RestTestProperties;
import com.chinasofti.testing.core.utils.HttpRequestElementUtil;
import com.chinasofti.testing.core.utils.HttpUtils;
import com.chinasofti.testing.dto.HttpRequestElement;
import com.chinasofti.testing.entity.ApiModule;
import com.chinasofti.testing.entity.ApiTestCase;
import com.chinasofti.testing.entity.ApiTestResult;
import com.chinasofti.testing.entity.CaseFolder;
import com.chinasofti.testing.entity.Environment;
import com.chinasofti.testing.entity.Project;
import com.chinasofti.testing.entity.Report;
import com.chinasofti.testing.service.IApiModuleService;
import com.chinasofti.testing.service.ICaseFolderService;
import com.chinasofti.testing.service.IProjectService;
import com.chinasofti.core.tool.utils.SpringUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.extra.spring.*;
import okhttp3.Response;
import com.chinasofti.testing.core.props.*;


public class ApiTestCaseRunner {

    protected final static Logger logger = LoggerFactory.getLogger(ApiTestCaseRunner.class);

    private HttpUtils httpUtils;

    private Project testProject;

    private CaseFolder folder;

    //private AssertClass assertClass;

    /*
     * 如下是用例执行情况统计需要的数据结构，后续计划通过观察者模式重构，暂时先放在这里
     */
    private TestResult testResult;
    private List<TestCaseResult> testCaseResultList;

    private String testBeginTime;

    // 测试多个类的时候，计第一个测试类开始测试的时间
    private Long testBeginTimeMills;

    private Map<Long, ApiModule> apiModuleCache = new HashMap<Long, ApiModule>();

    private Environment environment;

    private RestTestProperties properties;

    private BootUser operatorUser;

    private Report report;

    private List<ApiTestResult> apiTestResults = new ArrayList<ApiTestResult>();

    public Report getReport() {
        return report;
    }

    public List<ApiTestResult> getApiTestResults() {
        return apiTestResults;
    }


    private ApiTestCaseRunner(HttpUtils _httpUtils, Project project, Environment environment, RestTestProperties properties, BootUser operatorUser, CaseFolder caseFolder) {
        // TODO Auto-generated constructor stub
        this.httpUtils = _httpUtils;
        this.testProject = project;
        testResult = new TestResult();
        testCaseResultList = new ArrayList<>();
        this.properties = properties;
        this.environment = environment;
        this.operatorUser = operatorUser;
        this.folder = caseFolder;
        /*
        if( assertClass == null )
            this.assertClass = SpringUtil.getBean(AssertClass.class);
        else
        	this.assertClass = assertClass;
        */
    }

    public static ApiTestCaseRunner getRunner(RestTestProperties properties, Project project, Environment environment, BootUser operatorUser, CaseFolder caseFolder) {
        HttpUtils _httpUtils = new HttpUtils(environment.getDomain());
        ApiTestCaseRunner testCaseRunner = new ApiTestCaseRunner(_httpUtils, project, environment, properties, operatorUser, caseFolder);
        return testCaseRunner;
    }

    private ApiModule getApiModule(Long id) {

        ApiModule apiModule = apiModuleCache.get(id);
        if (apiModule == null) {
            IApiModuleService apiModuleService = SpringUtil.getBean(IApiModuleService.class);
            apiModule = apiModuleService.getById(id);
            apiModuleCache.put(id, apiModule);
        }
        return apiModule;
    }

    public void run(Long reportId, List<ApiTestCase> apiTestCases) {

        /*
         * 执行情况统计，后续重构
         */
        testBeginTime = new Date().toString();
        testBeginTimeMills = System.currentTimeMillis();
        boolean wasSuccessful = true;
        Integer caseCount = 0;
        Integer failureCount = 0;
        Integer ignoreCount = 0;
        /*
         * ----------------------
         */
        logger.debug("testCases.size :" + apiTestCases.size());
        caseCount = caseCount + apiTestCases.size();
        for (ApiTestCase testCase : apiTestCases) {
            TestStep testStep = new TestStep();
            HttpRequestElement request = JsonUtil.parse(testCase.getRequest(), HttpRequestElement.class);
            testStep.appendHeaders(HttpRequestElementUtil.toHeaderMap(request));
            ApiModule apiModule = getApiModule(testCase.getApiModuleId());
            testStep.setPath(apiModule.getPath());

            //---------------
            Long testCaseBeginTime = System.currentTimeMillis();
            TestCaseResult testCaseResult = new TestCaseResult();
            List<String> throwableLog = new ArrayList<String>();
            String description = "";
            //-----------------
            testStep.setParams(HttpRequestElementUtil.toQueryParams(request));
            testStep.setType(HttpRequestElementUtil.toHttpType(getApiModule(testCase.getApiModuleId()).getMethod()));
            String a = testStep.getType().toString();
            System.out.println(a);
            if(testStep.getType() != HttpType.GET && request.getBody() != null )
                testStep.setBody(request.getBody().getRaw());
            if( testStep.getType().toString().equals( "post" ) )
                testStep.setContentType( ContentType.JSON );
            if( testStep.getType().toString().equals( "put" ) )
                testStep.setContentType( ContentType.JSON );
            if (StringUtil.isBlank(testStep.getPath()))
                testStep.setPath(apiModule.getPath());
            List<String> pathVariables = HttpRequestElementUtil.toRestParams(request);
            if (!pathVariables.isEmpty()) {
                String path = testStep.getPath();
                for (String pathVariable : pathVariables) {
                    path = path + "/" + pathVariable;
                }
                testStep.setPath(path);
            }

            try {
                Response response = httpUtils.request(testStep);
                testCaseResult.setClassName(this.environment.getDomain() + testStep.getPath());
                testCaseResult.setMethodName(testCase.getName());

                //List<TestAssert> asserts = testCase.getAsserts();
                //AssertClass.AssertResult assertResult = this.assertClass.execute(asserts , response);

                if (response.code() == 200) {
                    testCaseResult.setStatus("success");
                } else {
                    testCaseResult.setStatus("failure");
                    failureCount++;
                    wasSuccessful = false;
                }

                //description = assertResult.desc.toString();

                ApiTestResult apiTestResult = new ApiTestResult();
                apiTestResult.setApiName(apiModule.getName());
                apiTestResult.setApiTestCaseName(testCase.getName());
                apiTestResult.setApiTestCaseNum(testCase.getNum());
                apiTestResult.setReportId(reportId);
                apiTestResult.setResponseCode(response.code());
                apiTestResult.setStatus(testCaseResult.getStatus());
                apiTestResult.setResponseTimes(System.currentTimeMillis() - testCaseBeginTime);
                this.apiTestResults.add(apiTestResult);
            } catch (Throwable failure) {
                failure.printStackTrace();
                //---------------------------
                wasSuccessful = false;
                testCaseResult.setStatus("failure");
                throwableLog.add(failure.toString());
                description = failure.toString();
                StackTraceElement[] st = failure.getStackTrace();
                for (StackTraceElement stackTraceElement : st) {
                    throwableLog.add("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + stackTraceElement);
                }
                failureCount++;
                //-----------------------------
            } finally {
                testCaseResult.setSpendTime(System.currentTimeMillis() - testCaseBeginTime + "ms");
                testCaseResult.setThrowableLog(throwableLog);
                testCaseResult.setDescription(description);
                testCaseResultList.add(testCaseResult);
            }
        }


        //--------------------
        Long totalTimes = System.currentTimeMillis() - testBeginTimeMills;

        logger.info("执行结果 : {}", wasSuccessful);
        logger.info("执行时间 : {}", totalTimes);
        logger.info("用例总数 : {}", caseCount);
        logger.info("失败数量 : {}", failureCount);
        logger.info("忽略数量 : {}", ignoreCount);

        testResult.setTestName(testProject.getName());

        testResult.setTestAll(caseCount);
        testResult.setTestPass(caseCount - failureCount);
        testResult.setTestFail(failureCount);
        testResult.setTestSkip(ignoreCount);

        testResult.setTestResult(testCaseResultList);

        testResult.setBeginTime(testBeginTime);
        testResult.setTotalTime(totalTimes + "ms");
        String htmlFileName = testProject.getName() + "/" + UUID.randomUUID().toString(true) + ".html";
        createReport(htmlFileName);

        this.report = new Report();
        report.setCaseCount(caseCount);
        report.setCreateBy(operatorUser.getUserName());
        report.setEnvironmentId(this.environment.getId());
        report.setFolderId(this.folder.getId());
        report.setFolderName(this.folder.getName());
        report.setProjectId(this.testProject.getId());
        report.setProjectName(this.testProject.getName());
        report.setReportUrl(properties.getReport().getHost() + "/" + htmlFileName);
        report.setRunDate(LocalDateTime.now());
        report.setType(Report.REPORT_TYPE_API);
        report.setId(reportId);


        //-------------
    }

    private void createReport(String html) {

        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream("template.html");
            String template = IoUtil.toString(inputStream);
            String jsonString = JsonUtil.toJson(testResult);
            template = template.replace("${resultData}", jsonString);
            logger.debug("report -> " + this.properties.getReport().getPath() + "/" + html);
            FileUtil.writeString(template, new File(this.properties.getReport().getPath() + "/" + html), "UTF-8");
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public void run1(Long reportId, ApiTestCase caseList) {

    }
}
