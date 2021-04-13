package com.chinasofti.core.changelog.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * 数据日志
 * </p>
 */
@Data
public class DataLog  implements Serializable {
 			
	private Long id;
	
	private String tenantId;
	
	private String moudle;
	
	private String tag;
	
	private String note;
	
	private String ip;
	
	private String oldData;
	
	private String newData;
	
	private String dataDiff;
	
	private String createDept;
	
	private String createBy;
	
	private String createTime;
	
	private String operationType;
}
