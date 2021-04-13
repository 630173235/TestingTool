package com.chinasofti.testing.service;

import com.chinasofti.testing.entity.ApiTestResult;
import com.chinasofti.testing.vo.ApiTestResultVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author Arvin
 * @since 2021-02-24
 */
public interface IApiTestResultService extends IService<ApiTestResult> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param apiTestResult
	 * @return
	 */
	IPage<ApiTestResultVO> selectApiTestResultPage(IPage<ApiTestResultVO> page, ApiTestResultVO apiTestResult);

}
