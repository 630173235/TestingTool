package com.chinasofti.testing.core.definiton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "测试断言", description = "测试断言")
public class TestAssert implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5600244535044827317L;

	@ApiModelProperty(value = "断言方法编号" , required = true)
	@NotBlank
	@Length(max = 30, message = "最多输入30个字符")
	private String method;
	
	@ApiModelProperty(value = "断言方法名称" , required = true)
	@NotBlank
	@Length(max = 30, message = "最多输入30个字符")
	private String name;
	
	@ApiModelProperty(value = "断言模板名称" , required = true)
	@NotBlank
	@Length(max = 30, message = "最多输入30个字符")
	private String type;
	
	@ApiModelProperty(value = "断言方法参数" , required = true)
	private List<TestAssertParameter> params = new ArrayList<TestAssertParameter>();
	
	public void appendParameter( TestAssertParameter parameter )
	{
		params.add( parameter );
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		for( TestAssertParameter testAssertParameter : params )
		{
			sb.append(  testAssertParameter.getName() + "=" + testAssertParameter.getValue().toString() + "," );
		}
		return sb.toString();	
	}
}
