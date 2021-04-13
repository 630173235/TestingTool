package com.chinasofti.testing.service;

import com.chinasofti.system.vo.RoleVO;
import com.chinasofti.testing.entity.CaseFolder;
import com.chinasofti.testing.vo.CaseFolderVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author Arvin
 * @since 2021-02-24
 */
public interface ICaseFolderService extends IService<CaseFolder> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param caseFolder
	 * @return
	 */
	IPage<CaseFolderVO> selectCaseFolderPage(IPage<CaseFolderVO> page, CaseFolderVO caseFolder);
	
	/**
	 * 树形结构
	 *
	 * @param tenantId
	 * @return
	 */
	List<CaseFolderVO> tree( long projectId );

}
