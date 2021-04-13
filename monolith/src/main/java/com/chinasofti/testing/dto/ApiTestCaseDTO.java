package com.chinasofti.testing.dto;

import com.chinasofti.testing.entity.ApiTestCase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据传输对象实体类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiTestCaseDTO extends ApiTestCase {
	private static final long serialVersionUID = 1L;

}
