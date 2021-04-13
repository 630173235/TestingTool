package com.chinasofti.testing.vo;

import com.chinasofti.testing.entity.Project;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 视图实体类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProjectVO对象", description = "ProjectVO对象")
public class ProjectVO extends Project {
	private static final long serialVersionUID = 1L;

}
