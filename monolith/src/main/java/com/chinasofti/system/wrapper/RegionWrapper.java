
package com.chinasofti.system.wrapper;

import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.node.ForestNodeMerger;
import com.chinasofti.core.tool.node.INode;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.core.tool.utils.SpringUtil;
import com.chinasofti.system.entity.Region;
import com.chinasofti.system.service.IRegionService;
import com.chinasofti.system.vo.RegionVO;

import java.util.List;
import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 *  @author Arvin Zhou
 */
public class RegionWrapper extends BaseEntityWrapper<Region, RegionVO> {

	private static IRegionService regionService;

	static {
		regionService = SpringUtil.getBean(IRegionService.class);
	}

	public static RegionWrapper build() {
		return new RegionWrapper();
	}

	@Override
	public RegionVO entityVO(Region region) {
		RegionVO regionVO = Objects.requireNonNull(BeanUtil.copy(region, RegionVO.class));
		Region parentRegion = regionService.getById(region.getParentCode());
		regionVO.setParentName(parentRegion.getName());
		return regionVO;
	}

	public List<INode> listNodeLazyVO(List<INode> list) {
		return ForestNodeMerger.merge(list);
	}

}
