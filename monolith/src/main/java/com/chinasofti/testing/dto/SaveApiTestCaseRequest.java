package com.chinasofti.testing.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Setter
@Getter
public class SaveApiTestCaseRequest implements Serializable{

	@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
	
	@JsonSerialize(using = ToStringSerializer.class)
	@NotNull
    private Long projectId;

	@JsonSerialize(using = ToStringSerializer.class)
	@NotNull
    private Long moduleId;
	
	@JsonSerialize(using = ToStringSerializer.class)
	@NotNull
    private Long folderId;
	
	@NotBlank
    private String name;

    private String description;

    private HttpRequestElement request;
}
