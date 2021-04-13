package com.chinasofti.testing.core.definiton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.chinasofti.testing.core.enums.HttpType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "测试用例", description = "测试用例")
public class TestCase implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "接口请求类型" , required = true)
	@NotBlank
	private HttpType methodType;
	
	@ApiModelProperty(value = "接口参数信息")
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@ApiModelProperty(value = "接口路径变量")
	private List<String> PathVariables = new ArrayList<String>();
	
	@ApiModelProperty(value = "请求路径")
	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void appendPathVariable( String pathVariable)
	{
		PathVariables.add( pathVariable );
	}
	
	public List<String> getPathVariables() {
		return PathVariables;
	}

	public void setPathVariables(List<String> pathVariables) {
		PathVariables = pathVariables;
	}

	@ApiModelProperty(value = "接口消息体")
	private String body;

	@ApiModelProperty(value = "测试预期结果" , required = true)
	@NotBlank
	private List<TestExpectResult> expectResults = new ArrayList<TestExpectResult>(); 
	
	@ApiModelProperty(value = "用例头部信息")
	private Map<String,String> headers = new HashMap<String,String>(); 
	
	@ApiModelProperty(value = "用例cokkies信息")
	private Map<String, String> cookies = new HashMap<String, String>(); 
	
	@ApiModelProperty(value = "用例名称" , required = true)
	@NotBlank
	@Length(max = 30, message = "最多输入30个字符")
	private String name;
	
	@ApiModelProperty(value = "用例编号" )
	@NotBlank
	@Length(max = 10, message = "最多输入10个字符")
	private String number;
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@ApiModelProperty(value = "用例描述")
	@Length(max = 100, message = "最多输入100个字符")
	private String description;
	
	@ApiModelProperty(value = "结果断言")
	private List<TestAssert> asserts = new ArrayList<TestAssert>();
	
	public void appendTestAssert( TestAssert testAssert )
	{
		asserts.add( testAssert );
	}
	
	public List<TestAssert> getAsserts() {
		return asserts;
	}

	public void setAsserts(List<TestAssert> asserts) {
		this.asserts = asserts;
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
 
	public void appendExpectResult( TestExpectResult expectResult )
	{
		expectResults.add( expectResult );
	}

	public void appendParams( String key , Object value )
	{
		params.put( key , value );
	}
	
	public HttpType getMethodType() {
		return methodType;
	}

	public void setMethodType(HttpType methodType) {
		this.methodType = methodType;
	}

	public List<TestExpectResult> getExpectResults() {
		return expectResults;
	}

	public void setExpectResults(List<TestExpectResult> expectResults) {
		this.expectResults = expectResults;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
