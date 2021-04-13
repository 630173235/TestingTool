package com.chinasofti.develop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;
import com.chinasofti.core.tool.constant.BootConstant;
import com.chinasofti.core.tool.utils.DateUtil;
import com.chinasofti.develop.support.Table;

/**
 * 应用生成表实体类
 *
 * @author Micro
 * @since 2020-12-17
 */
@TableName("develop_application")
@ApiModel(value = "Application对象", description = "应用生成表")
public class Application implements Serializable {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationCode() {
		if( applicationCode != null )
	    	applicationCode = applicationCode.replace("_", "-");
		return applicationCode;
	}
	
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode.replace("_", "-");
	}

	public Integer getApplicationPort() {
		return applicationPort;
	}

	public void setApplicationPort(Integer applicationPort) {
		this.applicationPort = applicationPort;
	}

	public String getApplicationPath() {
		return applicationPath;
	}

	public void setApplicationPath(String applicationPath) {
		this.applicationPath = applicationPath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<Table> getEntitis() {
		return entitis;
	}

	public void setEntitis(List<Table> entitis) {
		this.entitis = entitis;
	}

	public String getEntitisJson() {
		return entitisJson;
	}

	public void setEntitisJson(String entitisJson) {
		this.entitisJson = entitisJson;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private static final long serialVersionUID = 1L;

    public static String STATUS_CREATED = "created";
    
    public static String STATUS_PUBLISH = "publish";
    
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String applicationName;
    /**
     * 应用编码
     */
    @ApiModelProperty(value = "应用编码")
    private String applicationCode;
    /**
     * 应用端口
     */
    @ApiModelProperty(value = "应用端口")
    private Integer applicationPort;
    /**
     * 应用生成路径
     */
    @ApiModelProperty(value = "应用生成路径")
    private String applicationPath = BootConstant.DEFAULT_CODEGENERATOR_PATH;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    
    /**
     * 是否已删除
     */
    @ApiModelProperty(value = "是否已删除")
    @JsonIgnore
    private Integer isDeleted = 0;

    @ApiModelProperty(value = "数据表设计")
    @TableField(exist = false)
    private List<Table> entitis;
    
    @TableField(exist = true)
    @JsonIgnore
    private String entitisJson;
    
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status = STATUS_CREATED;
}
