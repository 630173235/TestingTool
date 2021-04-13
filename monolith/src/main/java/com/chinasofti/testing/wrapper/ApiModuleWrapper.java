package com.chinasofti.testing.wrapper;

import lombok.AllArgsConstructor;
import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.testing.entity.ApiModule;
import com.chinasofti.testing.vo.ApiModuleVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Arvin
 * @since 2021-02-24
 */
public class ApiModuleWrapper extends BaseEntityWrapper<ApiModule, ApiModuleVO>  {

    public static ApiModuleWrapper build() {
        return new ApiModuleWrapper();
    }

	@Override
	public ApiModuleVO entityVO(ApiModule apiModule) {
		ApiModuleVO apiModuleVO = BeanUtil.copy(apiModule, ApiModuleVO.class);

		return apiModuleVO;
	}

}
