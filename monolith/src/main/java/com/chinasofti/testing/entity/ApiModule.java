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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import com.chinasofti.core.tool.utils.DateUtil;

/**
 * 实体类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Data
@TableName("testing_api_module")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ApiModule对象", description = "ApiModule对象")
public class ApiModule extends BaseEntity {

	public final static Integer STATUS_DRAFT = 0;
	
	public final static Integer STATUS_WAITING= 1;
	
	public final static Integer STATUS_TESTING = 2;
	
    private static final long serialVersionUID = 1L;

    /**
     * api id
     */
    @ApiModelProperty(value = "api id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * api num
     */
    @ApiModelProperty(value = "api num")
    private String num;
    /**
     * project id
     */
    @ApiModelProperty(value = "project id")
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull
    private Long projectId;
    /**
     * api name
     */
    @ApiModelProperty(value = "api name")
    @NotBlank
    private String name;
    /**
     * api description
     */
    @ApiModelProperty(value = "api description")
    private String description;
    /**
     * method
     */
    @ApiModelProperty(value = "method")
    @NotBlank
    private String method;
    /**
     * request path
     */
    @ApiModelProperty(value = "request path")
    @NotBlank
    private String path;
    /**
     * request (JSON format)
     */
    @ApiModelProperty(value = "request (JSON format)")
    private String request;
    /**
     * request (JSON format)
     */
    @ApiModelProperty(value = "request (JSON format)")
    private String response;


}
