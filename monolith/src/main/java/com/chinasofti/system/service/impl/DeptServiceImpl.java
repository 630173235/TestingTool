
package com.chinasofti.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinasofti.core.secure.utils.SecureUtil;
import com.chinasofti.core.tool.constant.BootConstant;
import com.chinasofti.core.tool.node.ForestNodeMerger;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.core.tool.utils.StringPool;
import com.chinasofti.core.tool.utils.StringUtil;
import com.chinasofti.exception.ServiceException;
import com.chinasofti.system.entity.Dept;
import com.chinasofti.system.mapper.DeptMapper;
import com.chinasofti.system.service.IDeptService;
import com.chinasofti.system.vo.DeptVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 *  @author Arvin Zhou
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

	@Override
	public IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept) {
		return page.setRecords(baseMapper.selectDeptPage(page, dept));
	}

	@Override
	public List<DeptVO> tree(String tenantId) {
		return ForestNodeMerger.merge(baseMapper.tree(tenantId));
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		List<Dept> deptList = baseMapper.selectList(Wrappers.<Dept>query().lambda().eq(Dept::getTenantId, tenantId).in(Dept::getDeptName, Func.toStrList(deptNames)));
		if (deptList != null && deptList.size() > 0) {
			return deptList.stream().map(dept -> Func.toStr(dept.getId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return baseMapper.getDeptNames(Func.toLongArray(deptIds));
	}

	@Override
	public boolean submit(Dept dept) {
		if (Func.isEmpty(dept.getParentId())) {
			dept.setTenantId(SecureUtil.getTenantId());
			dept.setParentId(BootConstant.TOP_PARENT_ID);
			dept.setAncestors(String.valueOf(BootConstant.TOP_PARENT_ID));
		}
		if (dept.getParentId() > 0) {
			Dept parent = getById(dept.getParentId());
			if (Func.toLong(dept.getParentId()) == Func.toLong(dept.getId())) {
				throw new ServiceException("父节点不可选择自身!");
			}
			dept.setTenantId(parent.getTenantId());
			String ancestors = parent.getAncestors() + StringPool.COMMA + dept.getParentId();
			dept.setAncestors(ancestors);
		}
		dept.setIsDeleted(BootConstant.DB_NOT_DELETED);
		return saveOrUpdate(dept);
	}
	
	@Override
	public List<DeptVO> lazyTree(String tenantId, Long parentId) {
		if (SecureUtil.isAdministrator()) {
			tenantId = StringPool.EMPTY;
		}
		return ForestNodeMerger.merge(baseMapper.lazyTree(tenantId, parentId));
	}
}
