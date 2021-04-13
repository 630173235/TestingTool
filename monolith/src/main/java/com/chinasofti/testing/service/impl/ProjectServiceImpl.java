
package com.chinasofti.testing.service.impl;

import com.chinasofti.testing.entity.Project;
import com.chinasofti.testing.vo.ProjectVO;
import com.chinasofti.testing.mapper.ProjectMapper;
import com.chinasofti.testing.service.IProjectService;
import com.chinasofti.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Service
public class ProjectServiceImpl extends BaseServiceImpl<ProjectMapper, Project> implements IProjectService {

	@Override
	public IPage<ProjectVO> selectProjectPage(IPage<ProjectVO> page, ProjectVO project) {
		return page.setRecords(baseMapper.selectProjectPage(page, project));
	}

}
