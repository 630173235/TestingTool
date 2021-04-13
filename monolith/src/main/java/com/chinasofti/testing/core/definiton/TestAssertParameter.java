package com.chinasofti.testing.core.definiton;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "断言参数", description = "断言参数")
public class TestAssertParameter implements Serializable , Comparable<TestAssertParameter>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4475769983109068158L;

	@ApiModelProperty(value = "参数索引编号" , required = true)
	@NotBlank
	@Length(max = 30, message = "最多输入30个字符")
	private Integer index;
	
	@ApiModelProperty(value = "参数名称" , required = true)
	@NotBlank
	@Length(max = 30, message = "最多输入30个字符")
	private String name;

	@ApiModelProperty(value = "参数类型" )
	@Length(max = 30, message = "最多输入30个字符")
	private String type;
	
	@ApiModelProperty(value = "参数值" )
	@NotBlank
	private Object value;

	@Override
	public int compareTo(TestAssertParameter o) {
		// TODO Auto-generated method stub
		return this.index.compareTo( o.getIndex() );
	}
}
