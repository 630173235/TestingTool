package com.chinasofti.testing.core.definiton;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.chinasofti.testing.core.enums.ExpectResultType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用例期望结果", description = "用例期望结果")
public class TestExpectResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7166912705025662855L;

	@ApiModelProperty(value = "期望结果类型" , required = true)
	@NotBlank
	private ExpectResultType type;
	
	@ApiModelProperty(value = "期望值" , required = true)
	@NotBlank
	private String content;

	@ApiModelProperty(value = "断言类型" , required = true)
	@NotBlank
	private String verifyType;
	
	private String customPath;
	
	public String getCustomPath() {
		return customPath;
	}

	public void setCustomPath(String customPath) {
		this.customPath = customPath;
	}

	public String getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}

	public ExpectResultType getType() {
		return type;
	}

	public void setType(ExpectResultType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
