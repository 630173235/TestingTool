
package com.chinasofti.develop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 *  @author Arvin Zhou
 */
@Data
@TableName("blade_code")
@ApiModel(value = "Code对象", description = "Code对象")
public class Code implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 数据源主键
	 */
	@ApiModelProperty(value = "数据源主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long datasourceId;

	/**
	 * 模块名称
	 */
	@ApiModelProperty(value = "服务名称")
	private String serviceName;

	/**
	 * 模块名称
	 */
	@ApiModelProperty(value = "模块名称")
	private String codeName;

	/**
	 * 表名
	 */
	@ApiModelProperty(value = "表名")
	private String tableName;

	/**
	 * 实体名
	 */
	@ApiModelProperty(value = "表前缀")
	private String tablePrefix;

	/**
	 * 主键名
	 */
	@ApiModelProperty(value = "主键名")
	private String pkName;

	/**
	 * 基础业务模式
	 */
	@ApiModelProperty(value = "基础业务模式")
	private Integer baseMode;

	/**
	 * 包装器模式
	 */
	@ApiModelProperty(value = "包装器模式")
	private Integer wrapMode;

	/**
	 * 后端包名
	 */
	@ApiModelProperty(value = "后端包名")
	private String packageName;

	/**
	 * 后端路径
	 */
	@ApiModelProperty(value = "后端路径")
	private String apiPath;

	/**
	 * 前端路径
	 */
	@ApiModelProperty(value = "前端路径")
	private String webPath;

	/**
	 * 是否已删除
	 */
	@TableLogic
	@ApiModelProperty(value = "是否已删除")
	@JsonIgnore
	private Integer isDeleted = 0;

	@ApiModelProperty(value = "应用主键")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long applicationId;
}
