
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
import com.chinasofti.core.secure.BootUser;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.system.vo.RoleVO;

import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinasofti.testing.entity.ApiModule;
import com.chinasofti.testing.entity.CaseFolder;
import com.chinasofti.testing.entity.Environment;
import com.chinasofti.testing.vo.CaseFolderVO;
import com.chinasofti.testing.vo.EnvironmentVO;
import com.chinasofti.testing.vo.SelectItemVO;
import com.chinasofti.testing.wrapper.CaseFolderWrapper;
import com.chinasofti.testing.wrapper.EnvironmentWrapper;
import com.chinasofti.testing.service.ICaseFolderService;
import com.chinasofti.core.boot.ctrl.BootController;

/**
 *  控制器
 *
 * @author Arvin
 * @since 2021-02-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("testing/caseFolder")
@Api(value = "caseFolder", tags = "caseFolder接口")
public class CaseFolderController extends BootController {

	private ICaseFolderService caseFolderService;

	/**
	 * 详情
	 */
	@GetMapping("/detail/{id}")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入id")
	public R<CaseFolderVO> detail( @PathVariable("id") Long id ) {
		CaseFolder detail = caseFolderService.getById( id );
		if( detail == null )
		{
			return R.fail( "not found the data" );
		}
		return R.data(CaseFolderWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入caseFolder")
	public R<IPage<CaseFolderVO>> list(CaseFolder caseFolder, Query query) {
		IPage<CaseFolder> pages = caseFolderService.page(Condition.getPage(query), Condition.getQueryWrapper(caseFolder));
		return R.data(CaseFolderWrapper.build().pageVO(pages));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "列表", notes = "传入项目ID")
	public R<List<CaseFolderVO>> page(@ApiParam(value = "项目id", required = true) @RequestParam String projectId) {
		QueryWrapper<CaseFolder> queryWrapper = new QueryWrapper<CaseFolder>();
		queryWrapper.eq( "project_id", projectId ).eq( "is_deleted" , 0 );
		List<CaseFolder> caseFolderList = caseFolderService.list(queryWrapper);
		return R.data( CaseFolderWrapper.build().listVO(caseFolderList));
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入caseFolder")
	public R save(@Valid @RequestBody CaseFolder caseFolder) {
		return R.status(caseFolderService.save(caseFolder));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入caseFolder")
	public R update(@Valid @RequestBody CaseFolder caseFolder) {
		return R.status(caseFolderService.updateById(caseFolder));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入caseFolder")
	public R submit(@Valid @RequestBody CaseFolder caseFolder) {
		return R.status(caseFolderService.saveOrUpdate(caseFolder));
	}

	
	/**
	 * 删除 
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(caseFolderService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<CaseFolderVO>> tree( @ApiParam(value = "projectId", required = true) @RequestParam String projectId ) {
		List<CaseFolderVO> tree = caseFolderService.tree( Func.toLong(projectId) );
		return R.data(tree);
	}
	
	/**
	 * 下拉选项
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "下拉列表", notes = "传入项目id")
	public R<List<SelectItemVO>> select( @ApiParam(value = "项目ID", required = true ) @RequestParam Long projectId ) {
		QueryWrapper<CaseFolder> query = new QueryWrapper<CaseFolder>();
		query.eq( "project_id", projectId ).eq( "is_deleted" , 0 );
		List<CaseFolder> datas = caseFolderService.list(query);
		List<SelectItemVO> items = new ArrayList<SelectItemVO>();
		for( CaseFolder caseFolder : datas )
		{
			SelectItemVO item = new SelectItemVO();
			item.setId( caseFolder.getId() );
			item.setValue( caseFolder.getName() );
			items.add( item );
		}
		return R.data(items);
	}
}
