package com.chinasofti.testing.mapper;

import com.chinasofti.testing.entity.Project;
import com.chinasofti.testing.vo.ProjectVO;
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
public interface ProjectMapper extends BaseMapper<Project> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param project
	 * @return
	 */
	List<ProjectVO> selectProjectPage(IPage page, ProjectVO project);

}
