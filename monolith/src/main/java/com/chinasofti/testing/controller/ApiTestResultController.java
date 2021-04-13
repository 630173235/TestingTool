
package com.chinasofti.testing.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinasofti.core.boot.ctrl.BootController;
import com.chinasofti.core.mp.support.Condition;
import com.chinasofti.core.mp.support.Query;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.testing.entity.ApiTestResult;
import com.chinasofti.testing.service.IApiTestResultService;
import com.chinasofti.testing.vo.ApiTestResultVO;
import com.chinasofti.testing.wrapper.ApiTestResultWrapper;
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
@RequestMapping("testing/apiTestResult")
@Api(value = "apiTestResult", tags = "apiTestResult接口")
public class ApiTestResultController extends BootController {

	private IApiTestResultService apiTestResultService;

	/**
	 * 详情
	 */
	@GetMapping("/detail/{id}")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入id")
	public R<ApiTestResultVO> detail( @PathVariable("id") Long id ) {
		ApiTestResult detail = apiTestResultService.getById( id );
		if( detail == null )
		{
			return R.fail( "not found the data" );
		}
		return R.data(ApiTestResultWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入apiTestResult")
	public R<IPage<ApiTestResultVO>> list(ApiTestResult apiTestResult, Query query) {
		IPage<ApiTestResult> pages = apiTestResultService.page(Condition.getPage(query), Condition.getQueryWrapper(apiTestResult));
		return R.data(ApiTestResultWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入apiTestResult")
	public R<IPage<ApiTestResultVO>> page(ApiTestResultVO apiTestResult, Query query) {
		IPage<ApiTestResultVO> pages = apiTestResultService.selectApiTestResultPage(Condition.getPage(query), apiTestResult);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入apiTestResult")
	public R save(@Valid @RequestBody ApiTestResult apiTestResult) {
		return R.status(apiTestResultService.save(apiTestResult));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入apiTestResult")
	public R update(@Valid @RequestBody ApiTestResult apiTestResult) {
		return R.status(apiTestResultService.updateById(apiTestResult));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入apiTestResult")
	public R submit(@Valid @RequestBody ApiTestResult apiTestResult) {
		return R.status(apiTestResultService.saveOrUpdate(apiTestResult));
	}

	
	/**
	 * 删除 
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(apiTestResultService.removeByIds(Func.toLongList(ids)));
	}

	
}
