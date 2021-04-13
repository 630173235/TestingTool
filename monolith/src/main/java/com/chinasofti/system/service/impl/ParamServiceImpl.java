
package com.chinasofti.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinasofti.core.mp.base.BaseServiceImpl;
import com.chinasofti.system.entity.Param;
import com.chinasofti.system.mapper.ParamMapper;
import com.chinasofti.system.service.IParamService;
import com.chinasofti.system.vo.ParamVO;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 *  @author Arvin Zhou
 */
@Service
public class ParamServiceImpl extends BaseServiceImpl<ParamMapper, Param> implements IParamService {

	@Override
	public IPage<ParamVO> selectParamPage(IPage<ParamVO> page, ParamVO param) {
		return page.setRecords(baseMapper.selectParamPage(page, param));
	}

}
