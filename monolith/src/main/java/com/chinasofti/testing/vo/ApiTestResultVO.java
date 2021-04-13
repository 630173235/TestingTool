package com.chinasofti.testing.vo;

import com.chinasofti.testing.entity.ApiTestResult;
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
@ApiModel(value = "ApiTestResultVO对象", description = "ApiTestResultVO对象")
public class ApiTestResultVO extends ApiTestResult {
	private static final long serialVersionUID = 1L;

}
