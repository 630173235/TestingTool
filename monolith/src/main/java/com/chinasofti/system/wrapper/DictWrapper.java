
package com.chinasofti.system.wrapper;

import com.chinasofti.common.constant.CommonConstant;
import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.node.ForestNodeMerger;
import com.chinasofti.core.tool.node.INode;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.core.tool.utils.SpringUtil;
import com.chinasofti.system.entity.Dict;
import com.chinasofti.system.service.IDictService;
import com.chinasofti.system.vo.DictVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 *  @author Arvin Zhou
 */
public class DictWrapper extends BaseEntityWrapper<Dict, DictVO> {

	private static IDictService dictService;

	static {
		dictService = SpringUtil.getBean(IDictService.class);
	}

	public static DictWrapper build() {
		return new DictWrapper();
	}

	@Override
	public DictVO entityVO(Dict dict) {
		DictVO dictVO = BeanUtil.copy(dict, DictVO.class);
		if (Func.equals(dict.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			dictVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Dict parent = dictService.getById(dict.getParentId());
			dictVO.setParentName(parent.getDictValue());
		}
		return dictVO;
	}

	public List<INode> listNodeVO(List<Dict> list) {
		List<INode> collect = list.stream().map(dict -> BeanUtil.copy(dict, DictVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
