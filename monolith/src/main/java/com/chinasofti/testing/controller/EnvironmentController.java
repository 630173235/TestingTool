
package com.chinasofti.testing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.chinasofti.core.mp.support.Condition;
import com.chinasofti.core.mp.support.Query;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinasofti.testing.entity.CaseFolder;
import com.chinasofti.testing.entity.Environment;
import com.chinasofti.testing.vo.EnvironmentVO;
import com.chinasofti.testing.vo.SelectItemVO;
import com.chinasofti.testing.wrapper.EnvironmentWrapper;
import com.chinasofti.testing.service.IEnvironmentService;
import com.chinasofti.core.boot.ctrl.BootController;

/**
 *  控制器
 *
 * @author Arvin
 * @since 2021-02-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("testing/environment")
@Api(value = "environment", tags = "environment接口")
public class EnvironmentController extends BootController {

	private IEnvironmentService environmentService;

	/**
	 * 详情
	 */
	@GetMapping("/detail/{id}")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入id")
	public R<EnvironmentVO> detail( @PathVariable("id") Long id ) {
		Environment detail = environmentService.getById( id );
		if( detail == null )
		{
			return R.fail( "not found the data" );
		}
		return R.data(EnvironmentWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入environment")
	public R<IPage<EnvironmentVO>> list(Environment environment, Query query) {
		IPage<Environment> pages = environmentService.page(Condition.getPage(query), Condition.getQueryWrapper(environment));
		return R.data(EnvironmentWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "列表", notes = "传入项目ID")
	public R<List<EnvironmentVO>> page(@ApiParam(value = "项目id", required = true) @RequestParam String projectId) {
		QueryWrapper<Environment> queryWrapper = new QueryWrapper<Environment>();
		queryWrapper.eq( "project_id", projectId ).eq( "is_deleted" , 0 );
		List<Environment> envList = environmentService.list(queryWrapper);
		return R.data( EnvironmentWrapper.build().listVO( envList ));
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入environment")
	public R save(@Valid @RequestBody Environment environment) {
		return R.status(environmentService.save(environment));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入environment")
	public R update(@Valid @RequestBody Environment environment) {
		return R.status(environmentService.updateById(environment));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入environment")
	public R submit(@Valid @RequestBody Environment environment) {
		return R.status(environmentService.saveOrUpdate(environment));
	}

	
	/**
	 * 删除 
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(environmentService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 下拉选项
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "下拉列表", notes = "传入项目id")
	public R<List<SelectItemVO>> select( @ApiParam(value = "项目ID", required = true ) @RequestParam Long projectId ) {
		QueryWrapper<Environment> query = new QueryWrapper<Environment>();
		query.eq( "project_id", projectId ).eq( "is_deleted" , 0 );
		List<Environment> datas = environmentService.list(query);
		List<SelectItemVO> items = new ArrayList<SelectItemVO>();
		for( Environment environment : datas )
		{
			SelectItemVO item = new SelectItemVO();
			item.setId( environment.getId() );
			item.setValue( environment.getName() );
			items.add( item );
		}
		return R.data(items);
	}
}
