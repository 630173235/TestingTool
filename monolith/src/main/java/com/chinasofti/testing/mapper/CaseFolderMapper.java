package com.chinasofti.testing.mapper;

import com.chinasofti.testing.entity.CaseFolder;
import com.chinasofti.testing.vo.CaseFolderVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper 接口
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Mapper
public interface CaseFolderMapper extends BaseMapper<CaseFolder> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param caseFolder
	 * @return
	 */
	List<CaseFolderVO> selectCaseFolderPage(IPage page, CaseFolderVO caseFolder);

	/**
	 * 获取树形节点
	 *
	 * @param tenantId
	 * @param excludeRole
	 * @return
	 */
	List<CaseFolderVO> tree(long projectId);
}
