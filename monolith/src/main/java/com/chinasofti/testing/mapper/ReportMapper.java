package com.chinasofti.testing.mapper;

import com.chinasofti.testing.entity.Report;
import com.chinasofti.testing.vo.ReportVO;
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
public interface ReportMapper extends BaseMapper<Report> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param report
	 * @return
	 */
	List<ReportVO> selectReportPage(IPage page, ReportVO report);

}
