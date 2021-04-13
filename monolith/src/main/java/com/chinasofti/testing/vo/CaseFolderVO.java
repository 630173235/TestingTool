package com.chinasofti.testing.vo;

import java.util.ArrayList;
import java.util.List;

import com.chinasofti.core.tool.node.INode;
import com.chinasofti.testing.entity.CaseFolder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 视图实体类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CaseFolderVO对象", description = "CaseFolderVO对象")
public class CaseFolderVO extends CaseFolder implements INode{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<INode> children;
	
	@Override
	public List<INode> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}
}
