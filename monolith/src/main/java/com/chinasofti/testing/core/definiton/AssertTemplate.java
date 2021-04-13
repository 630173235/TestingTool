package com.chinasofti.testing.core.definiton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "断言模板", description = "断言模板")
public class AssertTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -610055991763021677L;

	@ApiModelProperty(value = "模板名称")
	private String name;
	
	@ApiModelProperty(value = "模板内容")
	private List<Assert> asserts = new ArrayList<Assert>();
	
	public void appendAssert( Assert assertt )
	{
		asserts.add( assertt );
	}
	
}
