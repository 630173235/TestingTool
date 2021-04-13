package com.chinasofti.testing.mapper;

import com.chinasofti.testing.entity.ApiTestResult;
import com.chinasofti.testing.vo.ApiTestResultVO;
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
public interface ApiTestResultMapper extends BaseMapper<ApiTestResult> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param apiTestResult
	 * @return
	 */
	List<ApiTestResultVO> selectApiTestResultPage(IPage page, ApiTestResultVO apiTestResult);

}
