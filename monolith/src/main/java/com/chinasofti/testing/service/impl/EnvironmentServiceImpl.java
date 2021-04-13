
package com.chinasofti.testing.service.impl;

import com.chinasofti.testing.entity.Environment;
import com.chinasofti.testing.vo.EnvironmentVO;
import com.chinasofti.testing.mapper.EnvironmentMapper;
import com.chinasofti.testing.service.IEnvironmentService;
import com.chinasofti.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Service
public class EnvironmentServiceImpl extends BaseServiceImpl<EnvironmentMapper, Environment> implements IEnvironmentService {

	@Override
	public IPage<EnvironmentVO> selectEnvironmentPage(IPage<EnvironmentVO> page, EnvironmentVO environment) {
		return page.setRecords(baseMapper.selectEnvironmentPage(page, environment));
	}

}
