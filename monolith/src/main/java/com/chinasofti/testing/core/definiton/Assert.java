package com.chinasofti.testing.core.definiton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "断言", description = "断言")
public class Assert implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -704161810500166298L;

	@ApiModelProperty(value = "断言方法")
	private String method;
	
	@ApiModelProperty(value = "断言方法的唯一标识")
	private String id;
	
	@ApiModelProperty(value = "断言方法的参数列表")
	private List<AssertParameter> params = new ArrayList<AssertParameter>();
	
	public void appendParameter( AssertParameter parameter )
	{
		params.add( parameter );
	}
}
