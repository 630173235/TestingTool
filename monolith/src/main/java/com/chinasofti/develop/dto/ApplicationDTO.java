package com.chinasofti.develop.dto;

import java.util.List;

import com.chinasofti.develop.entity.Application;
import com.chinasofti.develop.support.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ApplicationDTO对象", description = "实体生成对象")
public class ApplicationDTO extends Application{

	@ApiModelProperty(value = "数据源类型" ,  allowableValues="mysql,oracle,postgres" )
	private String dataSourceType = "mysql";
	
	@ApiModelProperty( value = "包名" )
	private String packageName = "com.chinasofti.demo";
}
