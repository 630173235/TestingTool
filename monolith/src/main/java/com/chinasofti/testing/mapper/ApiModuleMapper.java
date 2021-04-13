package com.chinasofti.testing.mapper;

import com.chinasofti.testing.entity.ApiModule;
import com.chinasofti.testing.vo.ApiModuleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper 接口
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Mapper
public interface ApiModuleMapper extends BaseMapper<ApiModule> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param apiModule
	 * @return
	 */
	List<ApiModule> selectApiModule(IPage page, ApiModuleVO apiModuleVO);
	
	List<ApiModule> listApiModule(ApiModuleVO apiModuleVO);

}
