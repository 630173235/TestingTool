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
@TableName("testing_case_folder")
@ApiModel(value = "CaseFolder对象", description = "CaseFolder对象")
public class CaseFolder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Folder ID
     */
    @ApiModelProperty(value = "Folder ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * Parent Folder ID
     */
    @ApiModelProperty(value = "Parent Folder ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    /**
     * Test case name
     */
    @ApiModelProperty(value = "folder name")
    private String name;
    /**
     * Test description
     */
    @ApiModelProperty(value = "folder description")
    private String description;
    /**
     * deleted
     */
    @ApiModelProperty(value = "deleted")
    private Integer isDeleted;

    @ApiModelProperty(value = "project id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long projectId;

}
