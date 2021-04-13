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
public class SaveApiModuleRequest implements Serializable{

	@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
	
	@JsonSerialize(using = ToStringSerializer.class)
	@NotNull
    private Long projectId;

	@NotBlank
    private String name;

    private String description;
    
    @NotBlank
    private String method;
    
    @NotBlank
    private String path;

    private HttpRequestElement request;

    private HttpResponseElement response;
}
