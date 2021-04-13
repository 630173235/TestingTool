package com.chinasofti.testing.service;

import com.chinasofti.testing.entity.Environment;
import com.chinasofti.testing.vo.EnvironmentVO;
import com.chinasofti.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author Arvin
 * @since 2021-02-24
 */
public interface IEnvironmentService extends BaseService<Environment> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param environment
	 * @return
	 */
	IPage<EnvironmentVO> selectEnvironmentPage(IPage<EnvironmentVO> page, EnvironmentVO environment);

}
