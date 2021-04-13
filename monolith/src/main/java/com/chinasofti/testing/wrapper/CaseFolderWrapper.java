package com.chinasofti.testing.wrapper;

import lombok.AllArgsConstructor;
import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.testing.entity.CaseFolder;
import com.chinasofti.testing.vo.CaseFolderVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Arvin
 * @since 2021-02-24
 */
public class CaseFolderWrapper extends BaseEntityWrapper<CaseFolder, CaseFolderVO>  {

    public static CaseFolderWrapper build() {
        return new CaseFolderWrapper();
    }

	@Override
	public CaseFolderVO entityVO(CaseFolder caseFolder) {
		CaseFolderVO caseFolderVO = BeanUtil.copy(caseFolder, CaseFolderVO.class);

		return caseFolderVO;
	}

}
