
package com.chinasofti.system.wrapper;

import com.chinasofti.common.constant.CommonConstant;
import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.node.ForestNodeMerger;
import com.chinasofti.core.tool.node.INode;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.core.tool.utils.SpringUtil;
import com.chinasofti.system.entity.Role;
import com.chinasofti.system.service.IRoleService;
import com.chinasofti.system.vo.RoleVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 *  @author Arvin Zhou
 */
public class RoleWrapper extends BaseEntityWrapper<Role, RoleVO> {

	private static IRoleService roleService;

	static {
		roleService = SpringUtil.getBean(IRoleService.class);
	}

	public static RoleWrapper build() {
		return new RoleWrapper();
	}

	@Override
	public RoleVO entityVO(Role role) {
		RoleVO roleVO = BeanUtil.copy(role, RoleVO.class);
		if (Func.equals(role.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			roleVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Role parent = roleService.getById(role.getParentId());
			roleVO.setParentName(parent.getRoleName());
		}
		return roleVO;
	}

	public List<INode> listNodeVO(List<Role> list) {
		List<INode> collect = list.stream().map(this::entityVO).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
