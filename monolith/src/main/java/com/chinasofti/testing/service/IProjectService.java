package com.chinasofti.testing.service;

import com.chinasofti.testing.entity.Project;
import com.chinasofti.testing.vo.ProjectVO;
import com.chinasofti.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author Arvin
 * @since 2021-02-24
 */
public interface IProjectService extends BaseService<Project> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param project
	 * @return
	 */
	IPage<ProjectVO> selectProjectPage(IPage<ProjectVO> page, ProjectVO project);

}
