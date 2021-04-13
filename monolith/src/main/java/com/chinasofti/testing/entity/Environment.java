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
@TableName("testing_environment")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Environment对象", description = "Environment对象")
public class Environment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Api Test Environment ID
     */
    @ApiModelProperty(value = "Api Test Environment ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
  @JsonSerialize(using = ToStringSerializer.class)
  private Long id;
    /**
     * Api Test Environment Name
     */
    @ApiModelProperty(value = "Api Test Environment Name")
    private String name;
    /**
     * project id
     */
    @ApiModelProperty(value = "project id")
    @JsonSerialize(using = ToStringSerializer.class)
  private Long projectId;
    /**
     * Environment type
     */
    @ApiModelProperty(value = "Environment type")
    private String type;
    /**
     * Environment description
     */
    @ApiModelProperty(value = "Environment description")
    private String description;
    /**
     * Api Test Domain
     */
    @ApiModelProperty(value = "Api Test Domain")
    private String domain;
    /**
     * Api Test Port
     */
    @ApiModelProperty(value = "Api Test Port")
    private Integer port;
    /**
     * Custom Data (JSON format)
     */
    @ApiModelProperty(value = "Custom Data (JSON format)")
    private String customData;


}
