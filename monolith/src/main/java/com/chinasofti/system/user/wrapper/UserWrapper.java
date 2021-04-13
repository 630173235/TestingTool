
package com.chinasofti.system.user.wrapper;

import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.core.tool.utils.SpringUtil;
import com.chinasofti.system.service.IDictService;
import com.chinasofti.system.user.entity.User;
import com.chinasofti.system.user.service.IUserService;
import com.chinasofti.system.user.vo.UserVO;

import java.util.List;

/**
 * 包装类,返回视图层所需的字段
 *
 *  @author Arvin Zhou
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	private static IUserService userService;

	private static IDictService dictService;

	static {
		userService = SpringUtil.getBean(IUserService.class);
		dictService = SpringUtil.getBean(IDictService.class);
	}

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = BeanUtil.copy(user, UserVO.class);
		List<String> roleName = userService.getRoleName(user.getRoleId());
		List<String> deptName = userService.getDeptName(user.getDeptId());
		userVO.setRoleName(Func.join(roleName));
		userVO.setDeptName(Func.join(deptName));
		String sexName = dictService.getValue("sex", Func.toInt(user.getSex()));
		userVO.setSexName(sexName);
		return userVO;
	}

}
