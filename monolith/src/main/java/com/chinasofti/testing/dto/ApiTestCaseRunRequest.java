package com.chinasofti.testing.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class ApiTestCaseRunRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7626736591858925616L;

	@NotNull
	@JsonSerialize(using = ToStringSerializer.class)
	private Long projectId;
	
	@NotNull
	@JsonSerialize(using = ToStringSerializer.class)
	private Long environmentId;
	
	@NotNull
	@JsonSerialize(using = ToStringSerializer.class)
	private Long folderId;
	
	private List<Long> testCaseIds;
}
