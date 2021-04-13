package com.chinasofti.testing.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class ApiTestCaseQuery implements Serializable{

    private String caseName;
    
    @JsonSerialize(using = ToStringSerializer.class)
    private Long moduleId;
    
    @JsonSerialize(using = ToStringSerializer.class)
    private Long folderId;
    
    private Integer status;
    
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    
}
