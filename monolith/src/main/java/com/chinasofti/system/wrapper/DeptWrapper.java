
package com.chinasofti.system.wrapper;

import com.chinasofti.common.constant.CommonConstant;
import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.node.ForestNodeMerger;
import com.chinasofti.core.tool.node.INode;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.core.tool.utils.SpringUtil;
import com.chinasofti.system.entity.Dept;
import com.chinasofti.system.service.IDeptService;
import com.chinasofti.system.vo.DeptVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 *  @author Arvin Zhou
 */
public class DeptWrapper extends BaseEntityWrapper<Dept, DeptVO> {

	private static IDeptService deptService;

	static {
		deptService = SpringUtil.getBean(IDeptService.class);
	}

	public static DeptWrapper build() {
		return new DeptWrapper();
	}

	@Override
	public DeptVO entityVO(Dept dept) {
		DeptVO deptVO = BeanUtil.copy(dept, DeptVO.class);
		if (Func.equals(dept.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			deptVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Dept parent = deptService.getById(dept.getParentId());
			deptVO.setParentName(parent.getDeptName());
		}
		return deptVO;
	}

	public List<INode> listNodeVO(List<Dept> list) {
		List<INode> collect = list.stream().map(dept -> BeanUtil.copy(dept, DeptVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
