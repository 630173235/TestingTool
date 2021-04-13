package com.chinasofti.testing.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "HttpRequestElement对象", description = "HttpRequestElement对象")
public class HttpRequestElement {

	 /**
	  * http header 数据
	  */
	 @ApiModelProperty(value = "Http Request Header定义")
	 private List<KeyValue> headers;
	 
	 /**
	  * rest 参数数据
	  * 如：http://xxx/xx/1
	  */
	 @ApiModelProperty(value = "Url参数定义,如http://xxx/xx/1")
	 private List<KeyValue> rest;
	 
	 /**
	  * 查询参数
	  * 如：http://xxxx/xx?key=value
	  */
	 @ApiModelProperty(value = "查询参数定义,如http://xxxx/xx?key=value")
	 private List<KeyValue> arguments;
	 
	 /**
	  * request body
	  */
	 @ApiModelProperty(value = "request body")
	 private Body body;
}
