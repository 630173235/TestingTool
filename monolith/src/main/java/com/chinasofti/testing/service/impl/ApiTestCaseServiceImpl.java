
package com.chinasofti.testing.service.impl;
import com.chinasofti.testing.core.props.RestTestProperties;
import com.chinasofti.testing.core.runner.ApiTestCaseRunner;
import com.chinasofti.testing.dto.ApiTestCaseQuery;
import com.chinasofti.testing.dto.ApiTestCaseRunOne;
import com.chinasofti.testing.dto.ApiTestCaseRunRequest;
import com.chinasofti.testing.entity.ApiTestCase;
import com.chinasofti.testing.entity.ApiTestResult;
import com.chinasofti.testing.entity.CaseFolder;
import com.chinasofti.testing.entity.Environment;
import com.chinasofti.testing.entity.Project;
import com.chinasofti.testing.entity.Report;
import com.chinasofti.testing.vo.ApiTestCasePage;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;

import com.chinasofti.testing.mapper.ApiTestCaseMapper;
import com.chinasofti.testing.service.IApiTestCaseService;
import com.chinasofti.testing.service.IApiTestResultService;
import com.chinasofti.testing.service.ICaseFolderService;
import com.chinasofti.testing.service.IEnvironmentService;
import com.chinasofti.testing.service.IProjectService;
import com.chinasofti.testing.service.IReportService;
import com.chinasofti.core.mp.base.BaseServiceImpl;
import com.chinasofti.core.secure.BootUser;

import java.util.List;
import java.lang.Long;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Service
@AllArgsConstructor
public class ApiTestCaseServiceImpl extends BaseServiceImpl<ApiTestCaseMapper, ApiTestCase> implements IApiTestCaseService {

	IProjectService projectService;
	
	IEnvironmentService environmentService;
	
	ICaseFolderService caseFolderService;
	
	IReportService reportService;
	
	IApiTestResultService apiTestResultService;
	/*public Long reportIdi;
	public ApiTestCaseServiceImpl() {

	}*/

	@Override
	public IPage<ApiTestCasePage> selectApiTestCasePage(IPage<ApiTestCasePage> page, ApiTestCaseQuery apiTestCaseQuery) {
		return page.setRecords(baseMapper.selectApiTestCasePage(page, apiTestCaseQuery));
	}

	@Override
	@Async
	@Transactional(rollbackFor = Exception.class)
	public void run(ApiTestCaseRunRequest request , BootUser bootUser , RestTestProperties properties) {
		// TODO Auto-generated method stub
		List<ApiTestCase> caseList = this.baseMapper.selectBatchIds(request.getTestCaseIds());
		Project project = projectService.getById( request.getProjectId() );
		CaseFolder caseFolder = caseFolderService.getById( request.getFolderId() );
		Environment environment = environmentService.getById( request.getEnvironmentId() );
		ApiTestCaseRunner runner = ApiTestCaseRunner.getRunner(properties, project, environment, bootUser, caseFolder);
		Long reportId = RandomUtil.randomLong(1, 9999999999999L);
		/*getReportId(reportId);*/
		runner.run( reportId , caseList);
		Report report = runner.getReport();
		List<ApiTestResult> resultList = runner.getApiTestResults();
		reportService.save( report );
		apiTestResultService.saveBatch( resultList );
	}

	/*public void getReportId(Long Id) {
		reportIdi=Id;
	}

	public Long putReportId(){
		return  reportIdi;
	}*/

	@Override
	@Async
	@Transactional(rollbackFor = Exception.class)
	public Long run1(ApiTestCaseRunOne request , BootUser bootUser , RestTestProperties properties) {
		// TODO Auto-generated method stub
		ApiTestCase caseList= this.baseMapper.selectById(request.getTestCaseId());
		Project project = projectService.getById( request.getProjectId() );
		CaseFolder caseFolder = caseFolderService.getById( request.getFolderId() );
		Environment environment = environmentService.getById( request.getEnvironmentId() );
		ApiTestCaseRunner runner = ApiTestCaseRunner.getRunner(properties, project, environment, bootUser, caseFolder);
		Long reportId = RandomUtil.randomLong(1, 9999999999999L);
		runner.run1( reportId ,caseList);
		Report report = runner.getReport();
		List<ApiTestResult> resultList = runner.getApiTestResults();
		reportService.save( report );
		apiTestResultService.saveBatch( resultList );
		return reportId;
	}
}
