package com.chinasofti.testing.wrapper;

import lombok.AllArgsConstructor;
import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.testing.entity.Report;
import com.chinasofti.testing.vo.ReportVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Arvin
 * @since 2021-02-24
 */
public class ReportWrapper extends BaseEntityWrapper<Report, ReportVO>  {

    public static ReportWrapper build() {
        return new ReportWrapper();
    }

	@Override
	public ReportVO entityVO(Report report) {
		ReportVO reportVO = BeanUtil.copy(report, ReportVO.class);

		return reportVO;
	}

}
