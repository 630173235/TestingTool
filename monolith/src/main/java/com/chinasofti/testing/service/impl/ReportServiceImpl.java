
package com.chinasofti.testing.service.impl;

import com.chinasofti.testing.entity.Report;
import com.chinasofti.testing.vo.ReportVO;
import com.chinasofti.testing.mapper.ReportMapper;
import com.chinasofti.testing.service.IReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements IReportService {

	@Override
	public IPage<ReportVO> selectReportPage(IPage<ReportVO> page, ReportVO report) {
		return page.setRecords(baseMapper.selectReportPage(page, report));
	}

}
