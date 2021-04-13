
package com.chinasofti.testing.service.impl;

import com.chinasofti.testing.entity.ApiTestResult;
import com.chinasofti.testing.vo.ApiTestResultVO;
import com.chinasofti.testing.mapper.ApiTestResultMapper;
import com.chinasofti.testing.service.IApiTestResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Service
public class ApiTestResultServiceImpl extends ServiceImpl<ApiTestResultMapper, ApiTestResult> implements IApiTestResultService {

	@Override
	public IPage<ApiTestResultVO> selectApiTestResultPage(IPage<ApiTestResultVO> page, ApiTestResultVO apiTestResult) {
		return page.setRecords(baseMapper.selectApiTestResultPage(page, apiTestResult));
	}

}
