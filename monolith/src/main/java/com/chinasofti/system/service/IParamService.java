
package com.chinasofti.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinasofti.core.mp.base.BaseService;
import com.chinasofti.system.entity.Param;
import com.chinasofti.system.vo.ParamVO;

/**
 * 服务类
 *
 *  @author Arvin Zhou
 */
public interface IParamService extends BaseService<Param> {

	/***
	 * 自定义分页
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ParamVO> selectParamPage(IPage<ParamVO> page, ParamVO param);

}
