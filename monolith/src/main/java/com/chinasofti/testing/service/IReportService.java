package com.chinasofti.testing.service;

import com.chinasofti.testing.entity.Report;
import com.chinasofti.testing.vo.ReportVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author Arvin
 * @since 2021-02-24
 */
public interface IReportService extends IService<Report> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param report
	 * @return
	 */
	IPage<ReportVO> selectReportPage(IPage<ReportVO> page, ReportVO report);

}
