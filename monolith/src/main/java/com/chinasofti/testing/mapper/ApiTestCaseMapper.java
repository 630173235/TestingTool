package com.chinasofti.testing.mapper;

import com.chinasofti.testing.dto.ApiTestCaseQuery;
import com.chinasofti.testing.entity.ApiTestCase;
import com.chinasofti.testing.vo.ApiTestCasePage;
import com.chinasofti.testing.vo.ApiTestCaseVO;
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
public interface ApiTestCaseMapper extends BaseMapper<ApiTestCase> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param apiTestCase
	 * @return
	 */
	List<ApiTestCasePage> selectApiTestCasePage(IPage page, ApiTestCaseQuery apiTestCaseQuery);

}
