package com.chinasofti.testing.vo;

import com.chinasofti.testing.entity.ApiTestCase;
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
@ApiModel(value = "ApiTestCaseVO对象", description = "ApiTestCaseVO对象")
public class ApiTestCaseVO extends ApiTestCase {
	private static final long serialVersionUID = 1L;

}
