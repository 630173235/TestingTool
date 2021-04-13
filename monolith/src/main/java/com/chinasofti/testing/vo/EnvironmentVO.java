package com.chinasofti.testing.vo;

import com.chinasofti.testing.entity.Environment;
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
@ApiModel(value = "EnvironmentVO对象", description = "EnvironmentVO对象")
public class EnvironmentVO extends Environment {
	private static final long serialVersionUID = 1L;

}
