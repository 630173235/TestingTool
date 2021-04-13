package com.chinasofti.testing.wrapper;

import lombok.AllArgsConstructor;
import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.testing.entity.ApiTestCase;
import com.chinasofti.testing.vo.ApiTestCaseVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Arvin
 * @since 2021-02-24
 */
public class ApiTestCaseWrapper extends BaseEntityWrapper<ApiTestCase, ApiTestCaseVO>  {

    public static ApiTestCaseWrapper build() {
        return new ApiTestCaseWrapper();
    }

	@Override
	public ApiTestCaseVO entityVO(ApiTestCase apiTestCase) {
		ApiTestCaseVO apiTestCaseVO = BeanUtil.copy(apiTestCase, ApiTestCaseVO.class);

		return apiTestCaseVO;
	}

}
