package com.chinasofti.testing.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class ApiTestCaseRunOne implements Serializable{

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

    private Long testCaseId;

}
