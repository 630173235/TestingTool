package com.chinasofti.testing.vo;

import com.chinasofti.testing.entity.ApiModule;
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
@ApiModel(value = "ApiModuleVO对象", description = "ApiModuleVO对象")
public class ApiModuleVO extends ApiModule {
	private static final long serialVersionUID = 1L;

}
