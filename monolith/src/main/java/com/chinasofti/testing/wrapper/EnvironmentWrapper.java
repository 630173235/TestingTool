package com.chinasofti.testing.wrapper;

import lombok.AllArgsConstructor;
import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.testing.entity.Environment;
import com.chinasofti.testing.vo.EnvironmentVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Arvin
 * @since 2021-02-24
 */
public class EnvironmentWrapper extends BaseEntityWrapper<Environment, EnvironmentVO>  {

    public static EnvironmentWrapper build() {
        return new EnvironmentWrapper();
    }

	@Override
	public EnvironmentVO entityVO(Environment environment) {
		EnvironmentVO environmentVO = BeanUtil.copy(environment, EnvironmentVO.class);

		return environmentVO;
	}

}
