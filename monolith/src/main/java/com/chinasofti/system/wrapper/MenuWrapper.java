
package com.chinasofti.system.wrapper;

import com.chinasofti.common.constant.CommonConstant;
import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.node.ForestNodeMerger;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.core.tool.utils.SpringUtil;
import com.chinasofti.system.entity.Menu;
import com.chinasofti.system.service.IDictService;
import com.chinasofti.system.service.IMenuService;
import com.chinasofti.system.vo.MenuVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 *  @author Arvin Zhou
 */
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO> {

	private static IMenuService menuService;

	private static IDictService dictService;

	static {
		menuService = SpringUtil.getBean(IMenuService.class);
		dictService = SpringUtil.getBean(IDictService.class);
	}

	public static MenuWrapper build() {
		return new MenuWrapper();
	}

	@Override
	public MenuVO entityVO(Menu menu) {
		MenuVO menuVO = BeanUtil.copy(menu, MenuVO.class);
		if (Func.equals(menu.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			menuVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Menu parent = menuService.getById(menu.getParentId());
			menuVO.setParentName(parent.getName());
		}
		String categoryName = dictService.getValue("menu_category", Func.toInt(menuVO.getCategory()));
		String actionName = dictService.getValue("button_func", Func.toInt(menuVO.getAction()));
		String isOpenName = dictService.getValue("yes_no", Func.toInt(menuVO.getIsOpen()));
		menuVO.setCategoryName(categoryName);
		menuVO.setActionName(actionName);
		menuVO.setIsOpenName(isOpenName);
		return menuVO;
	}


	public List<MenuVO> listNodeVO(List<Menu> list) {
		List<MenuVO> collect = list.stream().map(menu -> BeanUtil.copy(menu, MenuVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
