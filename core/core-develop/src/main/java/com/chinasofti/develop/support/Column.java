package com.chinasofti.develop.support;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Column对象", description = "数据字段元数据")
public class Column  implements Serializable {

	@ApiModelProperty(value = "字段数据类型" , required = true , allowableValues="integer,number,string,datetime,long,timestamp,text,boolean" )
	@NotBlank
	private String dataType;
	
	@ApiModelProperty(value = "字段数据长度" )
	@Length( min = 1 , message = "最小是1")
	private Integer dataLen;
	
	@ApiModelProperty(value = "浮点型数据小数点后精度" , allowableValues="range(1, 5)" )
	@Length( min = 1 , max =5 , message = "最小是1,最大是5")
	private Integer datePrecision;
	
	@ApiModelProperty(value = "是否主键" )
	private Boolean isPrimaryKey = false;
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getDataLen() {
		if( this.isPrimaryKey && dataType.equals( "long" )) return 64;
		if(dataType.equals( "string" )) return 255;
		if(dataType.equals( "int" )) return 10;
		if(dataType.equals( "number" )) return 10;
		return dataLen;
	}

	public void setDataLen(Integer dataLen) {
		this.dataLen = dataLen;
	}

	public Integer getDatePrecision() {
		if( datePrecision == null )
		{
			return 2;
		}
		return datePrecision;
	}

	public void setDatePrecision(Integer datePrecision) {
		this.datePrecision = datePrecision;
	}

	public Boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(Boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsNull() {
		if( this.isPrimaryKey ) return false;
		return isNull;
	}

	public void setIsNull(Boolean isNull) {
		this.isNull = isNull;
	}

	@ApiModelProperty(value = "字段名称" )
	@Length(max = 50, message = "最多输入50个字符")
	@NotBlank
	private String name;
	
	@ApiModelProperty(value = "字段编码" )
	@Length(max = 30, message = "最多输入30个字符")
	@NotBlank
	private String code;
	
	@ApiModelProperty(value = "是否为空" )
	private Boolean isNull = false;
	
}
