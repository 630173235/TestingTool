package com.chinasofti.testing.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "HttpResponseElement对象", description = "HttpResponseElement对象")
public class HttpResponseElement {

	@ApiModelProperty(value = "Http Response Header定义")
    private List<KeyValue> headers;
    
	@ApiModelProperty(value = "Http Response Status定义")
    private List<KeyValue> statusCode;
    
	@ApiModelProperty(value = "Http Response Body定义")
    private Body body;
}
