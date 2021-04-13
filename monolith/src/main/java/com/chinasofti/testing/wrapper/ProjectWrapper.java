package com.chinasofti.testing.wrapper;

import lombok.AllArgsConstructor;
import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.testing.entity.Project;
import com.chinasofti.testing.vo.ProjectVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Arvin
 * @since 2021-02-24
 */
public class ProjectWrapper extends BaseEntityWrapper<Project, ProjectVO>  {

    public static ProjectWrapper build() {
        return new ProjectWrapper();
    }

	@Override
	public ProjectVO entityVO(Project project) {
		ProjectVO projectVO = BeanUtil.copy(project, ProjectVO.class);

		return projectVO;
	}

}
