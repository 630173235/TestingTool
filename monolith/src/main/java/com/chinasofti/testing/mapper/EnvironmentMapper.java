package com.chinasofti.testing.mapper;

import com.chinasofti.testing.entity.Environment;
import com.chinasofti.testing.vo.EnvironmentVO;
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
public interface EnvironmentMapper extends BaseMapper<Environment> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param environment
	 * @return
	 */
	List<EnvironmentVO> selectEnvironmentPage(IPage page, EnvironmentVO environment);

}
