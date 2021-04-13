package com.chinasofti.testing.core.definiton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micrometer.core.instrument.util.JsonUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "测试项目", description = "测试项目")
public class TestProject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7177864587405177047L;

	@ApiModelProperty(value = "项目id" , required = true)
	@NotBlank
	private Long id ;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ApiModelProperty(value = "项目名称" , required = true)
	@NotBlank
	@Length(max = 30, message = "最多输入30个字符")
	private String name;
	
	@ApiModelProperty(value = "项目编号" , required = true)
	@NotBlank
	@Length(max = 30, message = "最多输入30个字符")
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ApiModelProperty(value = "项目描述")
	@Length(max = 100, message = "最多输入100个字符")
	private String description;
	
	@ApiModelProperty(value = "项目域名" , required = true)
	@NotBlank
	@Length(max = 100, message = "最多输入100个字符")
	private String baseUrl;

	@ApiModelProperty(value = "项目接口" , required = true)
	@NotBlank
	private List<TestSuit> suits = new ArrayList<TestSuit>();
	
	@ApiModelProperty(value = "项目公共头部信息")
	private Map<String,String> headers = new HashMap<String,String>();
	
	@ApiModelProperty(value = "项目公共cookies信息")
    private Map<String, String> cookies = new HashMap<String, String>(); 
	
	public void appendCookies( String key , String value )
	{
		cookies.put( key, value );
	}
	
	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	} 
	
	public void appendHeader( String key , String value )
	{
		headers.put( key , value );
	}
	
	public void appendTestSuit( TestSuit suit )
	{
		suits.add( suit );
	}
	
	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public List<TestSuit> getSuits() {
		return suits;
	}

	public void setSuits(List<TestSuit> suits) {
		this.suits = suits;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
