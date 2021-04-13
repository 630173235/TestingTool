package com.chinasofti.develop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TestCaseDemo {
    private String name;

    private String code;

    private String description;

    private String path;

    private String methodType;

    private Map<String,Object> params = new HashMap<>();

    private List<TestCaseExpectDTO> expectResults = new ArrayList<>();
}
