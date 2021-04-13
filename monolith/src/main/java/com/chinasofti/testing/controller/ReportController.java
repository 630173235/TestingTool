
package com.chinasofti.testing.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinasofti.core.boot.ctrl.BootController;
import com.chinasofti.core.mp.support.Condition;
import com.chinasofti.core.mp.support.Query;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.testing.entity.Report;
import com.chinasofti.testing.service.IReportService;
import com.chinasofti.testing.vo.ReportVO;
import com.chinasofti.testing.wrapper.ReportWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author Arvin
 * @since 2021-02-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("testing/report")
@Api(value = "report", tags = "report接口")
public class ReportController extends BootController {

	private IReportService reportService;

	/**
	 * 详情
	 */
	@GetMapping("/detail/{id}")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入id")
	public R<ReportVO> detail( @PathVariable("id") Long id ) {
		Report detail = reportService.getById( id );
		if( detail == null )
		{
			return R.fail( "not found the data" );
		}
		return R.data(ReportWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入report")
	public R<IPage<ReportVO>> list(Report report, Query query) {
		IPage<Report> pages = reportService.page(Condition.getPage(query), Condition.getQueryWrapper(report));
		return R.data(ReportWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入report")
	public R<IPage<ReportVO>> page(ReportVO report, Query query) {
		IPage<ReportVO> pages = reportService.selectReportPage(Condition.getPage(query), report);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入report")
	public R save(@Valid @RequestBody Report report) {
		return R.status(reportService.save(report));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入report")
	public R update(@Valid @RequestBody Report report) {
		return R.status(reportService.updateById(report));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入report")
	public R submit(@Valid @RequestBody Report report) {
		return R.status(reportService.saveOrUpdate(report));
	}

	
	/**
	 * 删除 
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(reportService.removeByIds(Func.toLongList(ids)));
	}

	
}
