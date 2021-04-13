
package com.chinasofti.develop.service;


import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import com.chinasofti.develop.entity.Code;

/**
 * 服务类
 *
 *  @author Arvin Zhou
 */
public interface ICodeService extends IService<Code> {

	/**
	 * 提交
	 *
	 * @param code
	 * @return
	 */
	boolean submit(Code code);

	List<Code> selectCodeByApplication( Long id );
}
