package com.chinasofti.testing.core.definiton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "测试项目接口", description = "测试项目接口")
public class TestSuit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4669934596420042542L;

	@ApiModelProperty(value = "接口名称" , required = true)
	@NotBlank
	@Length(max = 30, message = "最多输入30个字符")
	private String name;
	
	@ApiModelProperty(value = "接口描述")
	@Length(max = 30, message = "最多输入30个字符")
	private String description;
	
	@ApiModelProperty(value = "接口路径" , required = true)
	@NotBlank
	@Length(max = 30, message = "最多输入30个字符")
	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@ApiModelProperty(value = "接口测试用例" , required = true)
	@NotBlank
	List<TestCase> testCases = new ArrayList<TestCase>();

	@ApiModelProperty(value = "接口头部信息")
	private Map<String,String> headers = new HashMap<String,String>();
	
	@ApiModelProperty(value = "接口cookies信息")
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
	
	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void appendTestCase( TestCase testCase )
	{
		testCases.add( testCase );
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

	public List<TestCase> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
	
}
