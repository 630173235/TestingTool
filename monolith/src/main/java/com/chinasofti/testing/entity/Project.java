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
@TableName("testing_project")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Project对象", description = "Project对象")
public class Project extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Project id
     */
    @ApiModelProperty(value = "Project id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
  @JsonSerialize(using = ToStringSerializer.class)
  private Long id;
    /**
     * Project name
     */
    @ApiModelProperty(value = "Project name")
    private String name;
    /**
     * Project description
     */
    @ApiModelProperty(value = "Project description")
    private String description;


}
