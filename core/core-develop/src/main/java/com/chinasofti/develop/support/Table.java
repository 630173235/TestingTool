package com.chinasofti.develop.support;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Table对象", description = "数据表元数据")
public class Table  implements Serializable {
	
	@ApiModelProperty(value = "表名" , required = true)
	@NotBlank
	@Length(max = 50, message = "最多输入50个字符")
    private String name;
    
	@ApiModelProperty(value = "表编码" , required = true )
	@Length(max = 30, message = "最多输入30个字符")
	@NotBlank
    private String code;
	
	@ApiModelProperty(value = "表字段" , required = true)
	@NotBlank
	private List<Column> columns;
	
	@ApiModelProperty(value = "是否继承框架基类" )
	private Boolean hasSuperEntity = Boolean.FALSE;
}
