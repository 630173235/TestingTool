
package com.chinasofti.core.mp.base;

import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 基础业务接口
 *
 * @param <T>
 * @author Arvin Zhou
 */
public interface BaseService<T> extends IService<T> {

	/**
	 * 逻辑删除
	 *
	 * @param ids id集合(逗号分隔)
	 * @return boolean
	 */
	boolean deleteLogic(@NotEmpty List<Long> ids);

	/**
	 * 变更状态
	 *
	 * @param ids    id集合
	 * @param status 状态值
	 * @return
	 */
	boolean changeStatus(@NotEmpty List<Long> ids, Integer status);
}
