package com.chinasofti.testing.dto;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
/**
 * KeyValue
 * 
 * httpRequest 或 httpResponse基础数据类型
 * @author Arvin
 *
 */
@ApiModel(value = "KeyValue对象", description = "KeyValue对象")
public class KeyValue {

	/**
	 * key 名称，比如header中的key值
	 */
	@ApiModelProperty(value = "参数key")
    private String name;
    
    /**
     * value 值
     */
	@ApiModelProperty(value = "参数value")
    private String value;
    
    /**
     * value的实际数据类型，比如 Integer , Date , Text，File等
     */
	@ApiModelProperty(value = "value的实际数据类型，比如 Integer , Date , Text，File等")
	@JsonIgnore
    private String type = "Text";
    
    /**
     * 文件上传相关，当type为file的时候，相关提交请填写在此处
     */
	@ApiModelProperty(value = "文件上传相关，当type为file的时候，相关提交请填写在此处")
	@JsonIgnore
    private List<BodyFile> files;
    
    /**
     * 对key value属性的描述
     */
	@ApiModelProperty(value = "对key value属性的描述")
    private String description;
    
    /**
     * 该KeyVlaue属性可用标志，默认为true
     */
	@ApiModelProperty(value = "该KeyVlaue属性可用标志，默认为true")
	@JsonIgnore
    private Boolean enable = true;
    
    /**
     * 出现在url中的参数是否需要编码处理的标志，默认true
     */
	@ApiModelProperty(value = "出现在url中的参数是否需要编码处理的标志，默认true")
	@JsonIgnore
    private Boolean encode = true;
    
    /**
     * keyValue是否为必填项,默认为true
     */
	@ApiModelProperty(value = "keyValue是否为必填项,默认为true")
	@JsonIgnore
    private Boolean required = true;

    /**
     * 内容编码类型，默认 text/plain
     */
	@ApiModelProperty(value = "内容编码类型，默认 text/plain")
	@JsonIgnore
    private String contentType = "text/plain";
    
    public KeyValue() {
        this(null, null);
    }

    public KeyValue(String name, String value) {
        this(name, value, null);
    }

    public KeyValue(String name, String value, String description) {
        this(name, value, description, null);
    }

    public KeyValue(String name, String type, String value, boolean required, boolean enable) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.required = required;
        this.enable = enable;
        this.contentType = contentType;
    }

    public KeyValue(String name, String value, String description, String contentType) {
        this(name, value, description, contentType, true);
    }

    public KeyValue(String name, String value, String description, String contentType, boolean required) {
        this.name = name;
        this.value = value;
        this.description = description;
        this.enable = true;
        this.required = required;
    }

    public KeyValue(String name, String value, String description, boolean required) {
        this(name, value, description, "", required);
    }

    @JsonIgnore
    public boolean isValid() {
        return (StringUtils.isNotBlank(name) || "JSON-SCHEMA".equals(type)) && !StringUtils.equalsIgnoreCase(type, "file");
    }

    @JsonIgnore
    public boolean isFile() {
        return (CollectionUtils.isNotEmpty(files)) && StringUtils.equalsIgnoreCase(type, "file");
    }
}
