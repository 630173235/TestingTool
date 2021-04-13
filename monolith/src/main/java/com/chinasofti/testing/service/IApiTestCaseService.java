package com.chinasofti.testing.service;

import com.chinasofti.testing.core.props.RestTestProperties;
import com.chinasofti.testing.dto.ApiTestCaseQuery;
import com.chinasofti.testing.dto.ApiTestCaseRunOne;
import com.chinasofti.testing.dto.ApiTestCaseRunRequest;
import com.chinasofti.testing.entity.ApiTestCase;
import com.chinasofti.testing.vo.ApiTestCasePage;
import com.chinasofti.testing.vo.ApiTestCaseVO;
import com.chinasofti.core.mp.base.BaseService;
import com.chinasofti.core.secure.BootUser;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author Arvin
 * @since 2021-02-24
 */
public interface IApiTestCaseService extends BaseService<ApiTestCase> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param apiTestCaseQuery
	 * @return
	 */
	IPage<ApiTestCasePage> selectApiTestCasePage(IPage<ApiTestCasePage> page, ApiTestCaseQuery apiTestCaseQuery);

	/*Long putReportId();*/

	void run( ApiTestCaseRunRequest request ,BootUser bootUser , RestTestProperties properties);


	Long run1(ApiTestCaseRunOne request , BootUser bootUser , RestTestProperties properties);

}

