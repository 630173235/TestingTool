package com.chinasofti.testing.service;

import com.chinasofti.testing.entity.ApiModule;
import com.chinasofti.testing.vo.ApiModuleVO;
import com.chinasofti.core.mp.base.BaseService;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.constraints.NotEmpty;

/**
 *  服务类
 *
 * @author Arvin
 * @since 2021-02-24
 */
public interface IApiModuleService extends BaseService<ApiModule> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param apiModule
	 * @return
	 */
	IPage<ApiModule> selectApiModulePage(IPage<ApiModule> page, ApiModuleVO apiModule);

	/**
	 * 列表
	 *
	 * @param page
	 * @param apiModule
	 * @return
	 */
	List<ApiModule> listApiModule(ApiModuleVO apiModule);


	/**
	 * 依据ID查询复制
	 *
	 * @param ids id集合(逗号分隔)
	 * @return boolean
	 */
	boolean idcopy(@NotEmpty List<Long> ids);
}
