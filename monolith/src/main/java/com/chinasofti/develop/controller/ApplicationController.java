	
package com.chinasofti.develop.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import com.chinasofti.core.mp.support.Condition;
import com.chinasofti.core.mp.support.Query;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.constant.BootConstant;
import com.chinasofti.core.tool.jackson.JsonUtil;
import com.chinasofti.core.tool.utils.Func;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.chinasofti.develop.dto.ApplicationDTO;
import com.chinasofti.develop.entity.Application;
import com.chinasofti.develop.entity.Code;
import com.chinasofti.develop.entity.Datasource;
import com.chinasofti.develop.service.IApplicationService;
import com.chinasofti.develop.service.ICodeService;
import com.chinasofti.develop.service.IDatasourceService;
import com.chinasofti.develop.support.Table;
import com.chinasofti.develop.util.CodeGeneratorUtils;
import com.chinasofti.core.boot.ctrl.BootController;
import com.chinasofti.core.launch.constant.AppConstant;

/**
 * 应用生成表 控制器
 *
 * @author Micro
 * @since 2020-12-17
 */
@RestController
@AllArgsConstructor
@RequestMapping( AppConstant.APPLICATION_DEVELOP_NAME + "/application")
@Api(value = "应用生成", tags = "应用生成接口")
@Slf4j
public class ApplicationController extends BootController {

	private IApplicationService applicationService;

	private IDatasourceService datasourceService;
	
	private ICodeService codeService;
	
	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入application")
	public R<Application> detail(ApplicationDTO application) {
		Application detail = applicationService.getOne(Condition.getQueryWrapper(application));
		detail.setEntitis(  JsonUtil.parseArray( detail.getEntitisJson() , com.chinasofti.develop.support.Table.class )  );
		return R.data(detail);
	}

	@GetMapping("/listAll")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查看所有应用列表")
	public R<List<Application>> listAll() {
		Application application = new Application();
		application.setApplicationPath( null );
		application.setIsDeleted( 0 );
		List<Application> applications = applicationService.list(Condition.getQueryWrapper(application));
		for( Application _application : applications )
		{
			_application.setEntitis( JsonUtil.parseArray( _application.getEntitisJson() , com.chinasofti.develop.support.Table.class ) );
		}
		return R.data(applications);
	}
	
	/**
	 * 分页 应用生成表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入application")
	public R<IPage<Application>> list(Application application, Query query) {
		application.setApplicationPath( null );
		application.setIsDeleted( 0 );
		IPage<Application> pages = applicationService.page(Condition.getPage(query), Condition.getQueryWrapper(application));
		List<Application> applications = pages.getRecords();
		for( Application _application : applications )
		{
			_application.setEntitis( JsonUtil.parseArray( _application.getEntitisJson() , com.chinasofti.develop.support.Table.class ) );
		}
		return R.data(pages);
	}

	/**
	 * 新增 应用生成表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入application")
	public R save(@Valid @RequestBody Application application) {
		List<com.chinasofti.develop.support.Table> entitis = application.getEntitis();
		String json = JsonUtil.toJson( entitis );
		application.setEntitisJson( json );
		return R.status(applicationService.save(application));
	}

	/**
	 * 生成应用
	 */
	@GetMapping(value="/downloadApplication" , headers="Accept=application/octet-stream")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "下载应用", notes = "应用ID")
	public void downloadApplication( @ApiParam(value = "应用主键", required = true) @RequestParam String id , HttpServletResponse response ) {
		try
		{
			Application detail = applicationService.getById( id );
			Datasource datasource = datasourceService.getById( "1123598812738675201" );
			List<Code> codes = codeService.selectCodeByApplication( detail.getId() );
			CodeGeneratorUtils.generator( codes , detail.getApplicationPort() , datasource, response, true, "saber" );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 生成应用
	 */
	@PostMapping(value="/createApplication" , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE ,  headers="Accept=application/octet-stream")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "生成应用", notes = "传入application")
	public void createApplication( @Valid @RequestBody ApplicationDTO applicationDTO , HttpServletResponse response ) {
		List<com.chinasofti.develop.support.Table> entitis = applicationDTO.getEntitis();
		String json = JsonUtil.toJson( entitis );
		applicationDTO.setEntitisJson( json );
		Datasource datasource = datasourceService.getById( "1123598812738675201" );
		applicationDTO.setId( null );
		applicationService.createTable( applicationDTO , datasource);
		List<Code> codes = new ArrayList<Code>();
		for( Table table : applicationDTO.getEntitis() )
		{
			Code code = new Code();
			code.setApiPath( BootConstant.DEFAULT_CODEGENERATOR_PATH + "api" );
			code.setBaseMode( table.getHasSuperEntity() ? 2:1);
			code.setWrapMode( 2 );
			code.setCodeName( table.getName() );
			code.setDatasourceId(  1123598812738675201L  );
			code.setPackageName( applicationDTO.getPackageName() );
			code.setPkName( "id" );
			code.setServiceName( applicationDTO.getApplicationCode() );
			code.setTableName( table.getCode() );
			code.setTablePrefix( table.getCode().contains( "_" )?table.getCode().split( "_" )[0]:table.getCode() );
			code.setWebPath(  BootConstant.DEFAULT_CODEGENERATOR_PATH + "web"  );
			code.setIsDeleted( 0 );
			code.setApplicationId( applicationDTO.getId() );
			codes.add( code );
		}
		codeService.saveOrUpdateBatch(codes);
		
		CodeGeneratorUtils.generator( codes , applicationDTO.getApplicationPort() , datasource, response, true, "saber" );
	}
	
	/**
	 * 生成物理表
	 */
	@PostMapping("/createTable")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "创建实体表", notes = "传入application")
	public R createTable(@Valid @RequestBody ApplicationDTO applicationDTO) {
		List<com.chinasofti.develop.support.Table> entitis = applicationDTO.getEntitis();
		String json = JsonUtil.toJson( entitis );
		applicationDTO.setEntitisJson( json );
		Datasource datasource = datasourceService.getById( "1123598812738675201" );
		applicationDTO.setId( null );
		applicationService.createTable( applicationDTO , datasource);
		List<Code> codes = new ArrayList<Code>();
		for( Table table : applicationDTO.getEntitis() )
		{
			Code code = new Code();
			code.setApiPath( BootConstant.DEFAULT_CODEGENERATOR_PATH + "api" );
			code.setBaseMode( table.getHasSuperEntity() ? 2:1);
			code.setWrapMode( 2 );
			code.setCodeName( table.getName() );
			code.setDatasourceId(  1123598812738675201L  );
			code.setPackageName( applicationDTO.getPackageName() );
			code.setPkName( "id" );
			code.setServiceName( applicationDTO.getApplicationName() );
			code.setTableName( table.getCode() );
			code.setTablePrefix( table.getCode().contains( "_" )?table.getCode().split( "_" )[0]:table.getCode() );
			code.setWebPath(  BootConstant.DEFAULT_CODEGENERATOR_PATH + "web"  );
			code.setIsDeleted( 0 );
			code.setApplicationId( applicationDTO.getId() );
			codes.add( code );
		}
		codeService.saveOrUpdateBatch(codes);
		return R.status(true);
	}
	
	/**
	 * 修改 应用生成表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入application")
	public R update(@Valid @RequestBody Application application) {
		return R.status(applicationService.updateById(application));
	}

	/**
	 * 新增或修改 应用生成表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入application")
	public R submit(@Valid @RequestBody Application application) {
		return R.status(applicationService.saveOrUpdate(application));
	}

	
	/**
	 * 删除 应用生成表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(applicationService.removeByIds(Func.toLongList(ids)));
	}

	
}
