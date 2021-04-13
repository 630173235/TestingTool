package com.chinasofti.testing.wrapper;

import lombok.AllArgsConstructor;
import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.testing.entity.ApiTestResult;
import com.chinasofti.testing.vo.ApiTestResultVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Arvin
 * @since 2021-02-24
 */
public class ApiTestResultWrapper extends BaseEntityWrapper<ApiTestResult, ApiTestResultVO>  {

    public static ApiTestResultWrapper build() {
        return new ApiTestResultWrapper();
    }

	@Override
	public ApiTestResultVO entityVO(ApiTestResult apiTestResult) {
		ApiTestResultVO apiTestResultVO = BeanUtil.copy(apiTestResult, ApiTestResultVO.class);

		return apiTestResultVO;
	}

}
