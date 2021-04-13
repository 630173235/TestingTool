package com.chinasofti.testing.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;
import com.chinasofti.core.tool.utils.DateUtil;

/**
 * 实体类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Data
@TableName("testing_api_test_result")
@ApiModel(value = "ApiTestResult对象", description = "ApiTestResult对象")
public class ApiTestResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Test Result ID
     */
    @ApiModelProperty(value = "Test Result ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * Test Report ID
     */
    @ApiModelProperty(value = "Test Report ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long reportId;
    /**
     * Test case name
     */
    @ApiModelProperty(value = "Test case name")
    private String apiTestCaseName;
    /**
     * api name
     */
    @ApiModelProperty(value = "api name")
    private String apiName;
    /**
     * test case number
     */
    @ApiModelProperty(value = "test case number")
    private String apiTestCaseNum;
    /**
     * api resposne times
     */
    @ApiModelProperty(value = "api resposne times")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long responseTimes;
    /**
     * api resposne code
     */
    @ApiModelProperty(value = "api resposne code")
    private Integer responseCode;
    /**
     * test result status
     */
    @ApiModelProperty(value = "test result status")
    private String status;


}
