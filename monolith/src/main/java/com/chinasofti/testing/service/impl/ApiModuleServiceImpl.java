
package com.chinasofti.testing.service.impl;

import com.chinasofti.core.serialnumber.Sequence;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.jackson.JsonUtil;
import com.chinasofti.testing.entity.ApiModule;
import com.chinasofti.testing.vo.ApiModuleVO;
import com.chinasofti.testing.mapper.ApiModuleMapper;
import com.chinasofti.testing.service.IApiModuleService;
import com.chinasofti.core.mp.base.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.constraints.NotEmpty;

/**
 *  服务实现类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Service
@AllArgsConstructor
public class ApiModuleServiceImpl extends BaseServiceImpl<ApiModuleMapper, ApiModule> implements IApiModuleService {

	private Sequence<Long> camcSequence;

	@Override
	public IPage<ApiModule> selectApiModulePage(IPage<ApiModule> page, ApiModuleVO apiModule) {
		return page.setRecords(baseMapper.selectApiModule(page, apiModule));
	}

	@Override
	public List<ApiModule> listApiModule(ApiModuleVO apiModule) {
		// TODO Auto-generated method stub
		return baseMapper.listApiModule(apiModule);
	}

	@Override
	public boolean idcopy(@NotEmpty List<Long> ids) {

		List<ApiModule> list = baseMapper.selectBatchIds(ids);
		List<ApiModule> list0 = new ArrayList<ApiModule>();
		for (ApiModule id:list) {

			ApiModule apiModule = new ApiModule();

			apiModule.setDescription( id.getDescription());
			apiModule.setMethod( id.getMethod() );
			apiModule.setName( id.getName() );
			apiModule.setPath( id.getPath() );
			apiModule.setProjectId( id.getProjectId() );
			apiModule.setStatus(ApiModule.STATUS_DRAFT);
			if( id.getRequest() != null )
				apiModule.setRequest(JsonUtil.toJson( id.getRequest() ));
			if( id.getResponse() != null )
				apiModule.setResponse(JsonUtil.toJson( id.getResponse() ));
			apiModule.setNum( camcSequence.nextStr() );

			list0.add(apiModule);

		}
		if (list0.isEmpty()) {
			return false;
		}
		return this.saveBatch(list0);
	}
}
