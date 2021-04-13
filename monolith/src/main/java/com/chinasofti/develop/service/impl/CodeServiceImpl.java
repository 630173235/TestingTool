
package com.chinasofti.develop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.chinasofti.core.tool.constant.BootConstant;
import com.chinasofti.develop.entity.Code;
import com.chinasofti.develop.mapper.CodeMapper;
import com.chinasofti.develop.service.ICodeService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 *  @author Arvin Zhou
 */
@Service
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements ICodeService {

	@Override
	public boolean submit(Code code) {
		code.setIsDeleted(BootConstant.DB_NOT_DELETED);
		return saveOrUpdate(code);
	}

	@Override
	public List<Code> selectCodeByApplication(Long id) {
		// TODO Auto-generated method stub
		return baseMapper.selectCode(id);
	}

}
