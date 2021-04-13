
package com.chinasofti.testing.controller;

import com.chinasofti.testing.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.undertow.util.FileUtils;
import lombok.AllArgsConstructor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.chinasofti.core.mp.support.Condition;
import com.chinasofti.core.mp.support.Query;
import com.chinasofti.core.serialnumber.Sequence;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.jackson.JsonUtil;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.core.tool.utils.StringUtil;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinasofti.testing.core.runner.ApiModuleRunner;
import com.chinasofti.testing.entity.ApiModule;
import com.chinasofti.testing.entity.Environment;
import com.chinasofti.testing.vo.ApiModuleRunResult;
import com.chinasofti.testing.vo.ApiModuleVO;
import com.chinasofti.testing.vo.SelectItemVO;
import com.chinasofti.testing.wrapper.ApiModuleWrapper;
import com.chinasofti.testing.service.IApiModuleService;
import com.chinasofti.testing.service.IEnvironmentService;
import com.chinasofti.core.boot.ctrl.BootController;
import org.springframework.web.multipart.MultipartFile;

/**
 *  控制器
 *
 * @author Arvin
 * @since 2021-02-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("testing/ApiModule")
@Api(value = "ApiModule", tags = "ApiModule接口")
public class ApiModuleController extends BootController {

	private IApiModuleService apiModuleService;

	private Sequence<Long> camcSequence;
	
	private IEnvironmentService environmentService;
	
	/**
	 * 详情
	 */
	@GetMapping("/detail/{id}")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入id")
	public R<DetailApiModuleResponse> detail( @PathVariable("id") Long id ) {
		ApiModule detail = apiModuleService.getById( id );
		if( detail == null )
		{
			return R.fail( "not found the data" );
		}
		DetailApiModuleResponse detailApiModuleResponse = new DetailApiModuleResponse();
		detailApiModuleResponse.setId( detail.getId() );
		detailApiModuleResponse.setDescription( detail.getDescription() );
		detailApiModuleResponse.setMethod( detail.getMethod() );
		detailApiModuleResponse.setName( detail.getName() );
		detailApiModuleResponse.setPath( detail.getPath() );
		detailApiModuleResponse.setProjectId( detail.getProjectId() );

		if( StringUtil.isNotBlank( detail.getRequest()  ) )
		    detailApiModuleResponse.setRequest( JsonUtil.parse( detail.getRequest()  , HttpRequestElement.class ));
		else
			detailApiModuleResponse.setRequest( null );
		if( StringUtil.isNotBlank( detail.getResponse()  ) )
		    detailApiModuleResponse.setResponse( JsonUtil.parse( detail.getResponse()  , HttpResponseElement.class ));
		else
			detailApiModuleResponse.setResponse( null );
		detailApiModuleResponse.setNum( detail.getNum() );
		return R.data(detailApiModuleResponse);
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入apiModule")
	public R<List<ApiModule>> list(ApiModuleVO apiModule) {
		List<ApiModule> datas = apiModuleService.listApiModule(apiModule);
		return R.data(datas);
	}


	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入apiModule")
	public R<IPage<ApiModule>> page(ApiModuleVO apiModule, Query query) {
		IPage<ApiModule> pages = apiModuleService.selectApiModulePage(Condition.getPage(query), apiModule);
		return R.data(pages);
	}

	/**
	 * 新增0
	 */
	@PostMapping("/create00")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增0提交", notes = "传入SaveApiModuleRequest")
	public R create0(@Valid @RequestBody SaveApiModuleRequest saveApiModuleRequest) {
		ApiModule apiModule = new ApiModule();
		apiModule.setDescription( saveApiModuleRequest.getDescription() );
		apiModule.setMethod( saveApiModuleRequest.getMethod() );
		apiModule.setName( saveApiModuleRequest.getName() );
		apiModule.setPath( saveApiModuleRequest.getPath() );

		apiModule.setProjectId( saveApiModuleRequest.getProjectId() );

		apiModule.setStatus(ApiModule.STATUS_DRAFT);
		if( saveApiModuleRequest.getRequest() != null )
		    apiModule.setRequest(JsonUtil.toJson( saveApiModuleRequest.getRequest() ));
		if( saveApiModuleRequest.getResponse() != null )
		    apiModule.setResponse(JsonUtil.toJson( saveApiModuleRequest.getResponse() ));
		apiModule.setNum( camcSequence.nextStr() );
		return R.status(apiModuleService.save(apiModule));
	}

	/**
	 * 新增1
	 */
	@PostMapping("/create01")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增1等待", notes = "传入SaveApiModuleRequest")
	public R create1(@Valid @RequestBody SaveApiModuleRequest saveApiModuleRequest) {
		ApiModule apiModule = new ApiModule();
		apiModule.setDescription( saveApiModuleRequest.getDescription() );
		apiModule.setMethod( saveApiModuleRequest.getMethod() );
		apiModule.setName( saveApiModuleRequest.getName() );
		apiModule.setPath( saveApiModuleRequest.getPath() );

		apiModule.setProjectId( saveApiModuleRequest.getProjectId() );

		apiModule.setStatus(ApiModule.STATUS_WAITING);
		if( saveApiModuleRequest.getRequest() != null )
			apiModule.setRequest(JsonUtil.toJson( saveApiModuleRequest.getRequest() ));
		if( saveApiModuleRequest.getResponse() != null )
			apiModule.setResponse(JsonUtil.toJson( saveApiModuleRequest.getResponse() ));
		apiModule.setNum( camcSequence.nextStr() );
		return R.status(apiModuleService.save(apiModule));
	}


	/**
	 * 新增或更新
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或更新", notes = "传入SaveApiModuleRequest")
	public R save(@Valid @RequestBody SaveApiModuleRequest saveApiModuleRequest) {
		ApiModule apiModule = new ApiModule();
		apiModule.setId( saveApiModuleRequest.getId() );
		apiModule.setDescription( saveApiModuleRequest.getDescription() );
		apiModule.setMethod( saveApiModuleRequest.getMethod() );
		apiModule.setName( saveApiModuleRequest.getName() );
		apiModule.setPath( saveApiModuleRequest.getPath() );
		apiModule.setProjectId( saveApiModuleRequest.getProjectId() );
		apiModule.setStatus(ApiModule.STATUS_DRAFT);
		if( saveApiModuleRequest.getRequest() != null )
		    apiModule.setRequest(JsonUtil.toJson( saveApiModuleRequest.getRequest() ));
		if( saveApiModuleRequest.getResponse() != null )
		    apiModule.setResponse(JsonUtil.toJson( saveApiModuleRequest.getResponse() ));
		if(  saveApiModuleRequest.getId() == null )
			apiModule.setNum( camcSequence.nextStr() );
		return R.status(apiModuleService.saveOrUpdate(apiModule));
	}
	
	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "更新", notes = "传入SaveApiModuleRequest")
	public R update(@Valid @RequestBody SaveApiModuleRequest saveApiModuleRequest) {
		if( saveApiModuleRequest.getId() == null )
			return R.fail( "cannot found ths id in the request" );
		ApiModule apiModule = new ApiModule();
		apiModule.setId( saveApiModuleRequest.getId() );
		apiModule.setDescription( saveApiModuleRequest.getDescription() );
		apiModule.setMethod( saveApiModuleRequest.getMethod() );
		apiModule.setName( saveApiModuleRequest.getName() );
		apiModule.setPath( saveApiModuleRequest.getPath() );
		apiModule.setProjectId( saveApiModuleRequest.getProjectId() );
		if( saveApiModuleRequest.getRequest() != null )
		    apiModule.setRequest(JsonUtil.toJson( saveApiModuleRequest.getRequest() ));
		if( saveApiModuleRequest.getResponse() != null )
		    apiModule.setResponse(JsonUtil.toJson( saveApiModuleRequest.getResponse() ));
		return R.status(apiModuleService.updateById(apiModule));
	}
	
	/**
	 * 删除 
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(apiModuleService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 试运行
	 */
	@PostMapping("/run")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "接口试运行", notes = "传入ApiModuleRunRequest")
	public R<ApiModuleRunResult> run(@Valid @RequestBody RunApiModuleRequest runApiModuleRequest) {
		Environment environment = environmentService.getById(runApiModuleRequest.getEnvironmentId());
		ApiModuleRunner runner = ApiModuleRunner.getRunner(environment);
		ApiModuleRunResult apiModuleResult = runner.run( runApiModuleRequest );
		return R.data(apiModuleResult);
	}
	
	/**
	 * 下拉选项
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "下拉列表", notes = "传入项目ID")
	public R<List<SelectItemVO>> select( @ApiParam(value = "项目ID", required = true ) @RequestParam Long projectId ) {
		QueryWrapper<ApiModule> query = new QueryWrapper<ApiModule>();
		query.eq( "project_id", projectId ).eq( "is_deleted" , 0 );
		List<ApiModule> datas = apiModuleService.list(query);
		List<SelectItemVO> items = new ArrayList<SelectItemVO>();
		for( ApiModule apiModule : datas )
		{
			SelectItemVO item = new SelectItemVO();
			item.setId( apiModule.getId() );
			item.setValue( apiModule.getName() );
			items.add( item );
		}
		return R.data(items);
	}


	/**
	 * 查询后复制
	 */
	@GetMapping("/copy")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "查询复制", notes = "传入id")
	public R copy(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		Boolean copy = apiModuleService.idcopy(Func.toLongList(ids));
		if (copy == true) {
			return R.success("复制添加成功！");
		}else {
			return R.fail("查询不到");
		}

	}

	/**
	 * 复制
	 */
	@PostMapping("/copyOne")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "复制", notes = "传入id和name和description")
	public R copyOne(@Valid @RequestBody SaveApiModuleCopyOne saveApiModuleCopyOne) {
		ApiModule detail = apiModuleService.getById( saveApiModuleCopyOne.getId() );
		if( detail == null )
		{
			return R.fail( "not found the data" );
		}
		ApiModule apiModule = new ApiModule();

		apiModule.setName( saveApiModuleCopyOne.getName());
		apiModule.setDescription( saveApiModuleCopyOne.getDescription());

		apiModule.setNum(detail.getNum());
		apiModule.setProjectId(detail.getProjectId());
		apiModule.setMethod(detail.getMethod());
		apiModule.setPath(detail.getPath());
		apiModule.setRequest(detail.getRequest());
		apiModule.setResponse(detail.getResponse());


		apiModule.setStatus(detail.getStatus());
		apiModule.setIsDeleted(detail.getIsDeleted());

		return R.status(apiModuleService.save(apiModule));
	}


	/**
	 * 状态变更
	 */
	@PostMapping("/changeStatus")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "状态变更", notes = "传入ID和新的状态status")
	public R changeStatus(@ApiParam(value = "主键集合", required = true) @RequestParam String ids, Integer status) {
		return R.status(apiModuleService.changeStatus(Func.toLongList(ids), status));
	}

//	/**
//	 * 校验是否为json文件
//	 */
//	/**
//	 * 导入用户
//	 */
//	@PostMapping("/checkJson")
//	@ApiOperationSupport(order = 12)
//	@ApiOperation(value = "导入Json", notes = "请传入json文件")
//	public R checkJson(MultipartFile file, Integer isCovered) {
//		//该方法是否进入
////		System.out.println("传进来了");
//
//		//list集合存储stringBuilder
////		List<Object> list = new ArrayList<>();
//		String filename = file.getOriginalFilename();
//		if (StringUtils.isEmpty(filename)) {
//			throw new RuntimeException("请上传文件!");
//		}
//		if (!StringUtils.endsWithIgnoreCase(filename, ".json")){
//			throw new RuntimeException("请上传正确的json文件!");
//		}
//
//		Object Json = null;
//		try {
//			Json = JsonUtil.readTree(file.getInputStream());
//		} finally {
//			if(Json == null)
//				return R.fail("Json数据有误");
//			else
//				return R.data(Json,"Json数据正确");
//		}
//
//
//		//将Json对象返回
////		return R.data(Json);
//	}

	/**
	 * 校验是否为json文件
	 */
	/**
	 * 导入用户
	 */
	@PostMapping("/checkJson")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "导入Json", notes = "请传入json文件")
	public R checkJson(MultipartFile file) {
		String json = " ";

		try {
			json = FileUtils.readFile(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.data(json);
	}

}
