
package com.chinasofti.testing.service.impl;

import com.chinasofti.core.tool.node.ForestNodeMerger;
import com.chinasofti.testing.entity.CaseFolder;
import com.chinasofti.testing.vo.CaseFolderVO;
import com.chinasofti.testing.mapper.CaseFolderMapper;
import com.chinasofti.testing.service.ICaseFolderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Service
public class CaseFolderServiceImpl extends ServiceImpl<CaseFolderMapper, CaseFolder> implements ICaseFolderService {

	@Override
	public IPage<CaseFolderVO> selectCaseFolderPage(IPage<CaseFolderVO> page, CaseFolderVO caseFolder) {
		return page.setRecords(baseMapper.selectCaseFolderPage(page, caseFolder));
	}

	@Override
	public List<CaseFolderVO> tree( long projectId ) {
		return ForestNodeMerger.merge(baseMapper.tree(projectId));
	}

}
