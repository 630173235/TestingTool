package com.chinasofti.testing.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class ApiTestCasePage implements Serializable{

	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
    private String num;
    private String caseName;
    
    @JsonSerialize(using = ToStringSerializer.class)
    private Long moduleId;
    
    @JsonSerialize(using = ToStringSerializer.class)
    private Long folderId;
    private Integer status;
    private String description;
    private String createBy;
    private String folderName;
    private String moduleName;
    
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    private String projectName;
    
}
