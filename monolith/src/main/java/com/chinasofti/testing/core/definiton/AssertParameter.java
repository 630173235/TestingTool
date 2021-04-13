package com.chinasofti.testing.core.definiton;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "断言方法参数", description = "断言方法参数")
public class AssertParameter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4475769983109068158L;

	@ApiModelProperty(value = "参数索引,位置标识")
	private Integer index;
	
	@ApiModelProperty(value = "参数名称")
	private String name;

	@ApiModelProperty(value = "参数类型")
	private String type;
}
