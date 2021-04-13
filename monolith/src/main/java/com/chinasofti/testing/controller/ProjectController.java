
package com.chinasofti.testing.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinasofti.core.boot.ctrl.BootController;
import com.chinasofti.core.mp.support.Condition;
import com.chinasofti.core.mp.support.Query;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.testing.entity.Project;
import com.chinasofti.testing.service.IProjectService;
import com.chinasofti.testing.vo.ProjectVO;
import com.chinasofti.testing.vo.SelectItemVO;
import com.chinasofti.testing.wrapper.ProjectWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *  控制器
 *
 * @author Arvin
 * @since 2021-02-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("testing/project")
@Api(value = "project", tags = "project接口")
public class ProjectController extends BootController {

	private IProjectService projectService;

	/**
	 * 详情
	 */
	@GetMapping("/detail/{id}")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入id")
	public R<ProjectVO> detail( @PathVariable("id") Long id ) {
		Project detail = projectService.getById( id );
		if( detail == null )
		{
			return R.fail( "not found the data" );
		}
		return R.data(ProjectWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入project")
	public R<IPage<ProjectVO>> page(Project project, Query query) {
		IPage<Project> pages = projectService.page(Condition.getPage(query), Condition.getQueryWrapper(project));
		return R.data(ProjectWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "列表")
	public R<List<ProjectVO>> list() {
		List<Project> projects = projectService.list();
		return R.data(ProjectWrapper.build().listVO(projects));
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入project")
	public R save(@Valid @RequestBody Project project) {
		return R.status(projectService.save(project));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入project")
	public R update(@Valid @RequestBody Project project) {
		return R.status(projectService.updateById(project));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入project")
	public R submit(@Valid @RequestBody Project project) {
		return R.status(projectService.saveOrUpdate(project));
	}

	
	/**
	 * 删除 
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(projectService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 下拉选项
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "下拉列表", notes = "传入项目id")
	public R<List<SelectItemVO>> select() {
		QueryWrapper<Project> query = new QueryWrapper<Project>();
		query.eq( "is_deleted" , 0 );
		List<Project> datas = projectService.list(query);
		List<SelectItemVO> items = new ArrayList<SelectItemVO>();
		for( Project project : datas )
		{
			SelectItemVO item = new SelectItemVO();
			item.setId( project.getId() );
			item.setValue( project.getName() );
			items.add( item );
		}
		return R.data(items);
	}
}
