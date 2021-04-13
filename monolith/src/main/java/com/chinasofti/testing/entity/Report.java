package com.chinasofti.testing.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
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
@TableName("testing_report")
@ApiModel(value = "Report对象", description = "Report对象")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String REPORT_TYPE_API = "api";
    
    /**
     * Test Report ID
     */
    @ApiModelProperty(value = "Test Report ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * Test Report type
     */
    @ApiModelProperty(value = "Test Report type")
    private String type;
    /**
     * Project ID this test belongs to
     */
    @ApiModelProperty(value = "Project ID this test belongs to")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;

    @ApiModelProperty(value = "project name")
    private String projectName;
    /**
     * Environment ID
     */
    @ApiModelProperty(value = "Environment ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long environmentId;

    /**
     * report create date
     */
    @ApiModelProperty(value = "report create date")
    private LocalDateTime runDate;
    /**
     * report create date
     */
    @ApiModelProperty(value = "report create date")
    private String reportUrl;
    /**
     * api test case folder id
     */
    @ApiModelProperty(value = "api test case folder id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long folderId;
    @ApiModelProperty(value = "folder name")
    private String folderName;
    /**
     * case count
     */
    @ApiModelProperty(value = "case count")
    private Integer caseCount;
    /**
     * create user name
     */
    @ApiModelProperty(value = "create user name")
    private String createBy;



}
