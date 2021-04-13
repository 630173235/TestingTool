package com.chinasofti.testing.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "Http Body", description = "Http Body")
public class Body {
	
	/**
	 * body提交的数据类型
	 * Form Data  即multipart/form-data 表单
	 * WWW_FORM   x-www-from-urlencoded 
	 * Raw   文本
	 * JSON  json形式文本
	 * XML   xml形式文本
	 * BINARY 二进制流内容
	 */
	@ApiModelProperty(value = "body提交的数据类型")
    private String type;
    
    /**
     * 当type为 raw json  xml时，提交的内容填写的此处
     */
	@ApiModelProperty(value = "当type为 raw json  xml时，提交的内容")
    private String raw;
    
    /**
     * 预留字段，主要对xml或json的Schema定义，暂不关注
     */
	@ApiModelProperty(value = "预留字段，主要对xml或json的Schema定义，暂不关注")
	@JsonIgnore
    private String format;
    
    /**
     * 当type为Form Data或WWW_FORM等已keyvalue形式描述的body信息时，提交内容填写在此处
     */
	@ApiModelProperty(value = "当type为Form Data或WWW_FORM等已keyvalue形式描述的body信息时，提交的内容")
	@JsonIgnore
    private List<KeyValue> kvs;
    
    /**
     * 当type为BINARY时，提交内容填写在此处，并且KeyValue中的type为file
     */
	@ApiModelProperty(value = "当type为BINARY时，提交内容填写在此处，并且KeyValue中的type为file")
	@JsonIgnore
    private List<KeyValue> binary;

    public final static String KV = "KeyValue";
    public final static String FORM_DATA = "Form Data";
    public final static String WWW_FROM = "WWW_FORM";
    public final static String RAW = "Raw";
    public final static String BINARY = "BINARY";
    public final static String JSON = "JSON";
    public final static String XML = "XML";

    @JsonIgnore
    public boolean isValid() {
        if (this.isKV()) {
            return kvs.stream().anyMatch(KeyValue::isValid);
        } else {
            return StringUtils.isNotBlank(raw);
        }
    }

    @JsonIgnore
    public boolean isKV() {
        if (StringUtils.equals(type, FORM_DATA) || StringUtils.equals(type, WWW_FROM)
                || StringUtils.equals(type, BINARY)) {
            return true;
        } else {
            return false;
        }
    }

    @JsonIgnore
    public boolean isOldKV() {
        if (StringUtils.equals(type, KV)) {
            return true;
        } else {
            return false;
        }
    }

    @JsonIgnore
    public boolean isBinary() {
        return StringUtils.equals(type, BINARY);
    }

    @JsonIgnore
    public boolean isJson() {
        return StringUtils.equals(type, JSON);
    }

    @JsonIgnore
    public boolean isXml() {
        return StringUtils.equals(type, XML);
    }

    @JsonIgnore
    public void initKvs() {
        this.kvs = new ArrayList<>();
        this.kvs.add(new KeyValue());
    }

    @JsonIgnore
    public void initBinary() {
        this.binary = new ArrayList<>();
        this.binary.add(new KeyValue());
    }

}
