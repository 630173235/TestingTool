package com.chinasofti.testing.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.chinasofti.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("testing_api_test_case")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ApiTestCase对象", description = "ApiTestCase对象")
public class ApiTestCase extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public final static Integer STATUS_PREPARE = 0;
	
	public final static Integer STATUS_UNDERWAY = 1;
	
	public final static Integer STATUS_COMPLETED = 2;
	
    /**
     * Test Case ID
     */
    @ApiModelProperty(value = "Test Case ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * Project ID this test belongs to
     */
    @ApiModelProperty(value = "Project ID this test belongs to")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    /**
     * Test case name
     */
    @ApiModelProperty(value = "Test case name")
    private String name;
    /**
     * api definition id
     */
    @ApiModelProperty(value = "api module id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long apiModuleId;
    /**
     * api test case folder id
     */
    @ApiModelProperty(value = "api test case folder id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long folderId;
    /**
     * Test description
     */
    @ApiModelProperty(value = "Test description")
    private String description;
    /**
     * request (JSON format)
     */
    @ApiModelProperty(value = "request (JSON format)")
    private String request;
    /**
     * api test case num
     */
    @ApiModelProperty(value = "api test case num")
    private String num;


}
