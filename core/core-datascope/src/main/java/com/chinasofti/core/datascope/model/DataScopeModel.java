package com.chinasofti.core.datascope.model;

import lombok.Data;
import com.chinasofti.core.datascope.constant.DataScopeConstant;
import com.chinasofti.core.datascope.enums.DataScopeEnum;

import java.io.Serializable;

/**
 * 数据权限实体类
 */
@Data
public class DataScopeModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 资源编号
	 */
	private String resourceCode;
	/**
	 * 数据权限字段
	 */
	private String scopeColumn = DataScopeConstant.DEFAULT_COLUMN;
	/**
	 * 数据权限规则
	 */
	private Integer scopeType = DataScopeEnum.ALL.getType();
	/**
	 * 可见字段
	 */
	private String scopeField;
	/**
	 * 数据权限规则值
	 */
	private String scopeValue;

}
