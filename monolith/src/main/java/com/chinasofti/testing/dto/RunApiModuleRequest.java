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
public class RunApiModuleRequest implements Serializable{
    
    @NotBlank
    private String method;
    
    @NotBlank
    private String path;

    @NotNull
    private HttpRequestElement request;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long environmentId;
}
