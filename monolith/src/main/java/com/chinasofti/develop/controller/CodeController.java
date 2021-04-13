
package com.chinasofti.develop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import cn.hutool.core.lang.generator.Generator;
import cn.hutool.core.util.ZipUtil;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import com.chinasofti.core.boot.ctrl.BootController;
import com.chinasofti.core.launch.constant.AppConstant;
import com.chinasofti.core.mp.support.Condition;
import com.chinasofti.core.mp.support.Query;
import com.chinasofti.core.secure.annotation.PreAuth;
import com.chinasofti.core.serialnumber.Sequence;
import com.chinasofti.core.serialnumber.persistent.SeqSynchronizer;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.constant.RoleConstant;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.develop.entity.Code;
import com.chinasofti.develop.entity.Datasource;
import com.chinasofti.develop.service.ICodeService;
import com.chinasofti.develop.service.IDatasourceService;
import com.chinasofti.develop.support.CodeGenerator;
import com.chinasofti.develop.util.CodeGeneratorUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 控制器
 *
 *  @author Arvin Zhou
 */
//@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping( AppConstant.APPLICATION_DEVELOP_NAME + "/code")
@Api(value = "代码生成", tags = "代码生成")
//@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
@Slf4j
public class CodeController extends BootController {

	private ICodeService codeService;
	private IDatasourceService datasourceService;

	private Sequence<Long> camcSequence;

	private SeqSynchronizer seqSynchronizer;
	
	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入code")
	public R<Code> detail(Code code) {
		Code detail = codeService.getOne(Condition.getQueryWrapper(code));
		return R.data(detail);
	}

	@GetMapping("/sequence")
	public R<Map<String, Object>> getSequence(@RequestParam(required = false) Integer size) {
		size = (size == null || size <= 0) ? 10 : size;
		List<String> list = new ArrayList<>(size);
		for (int i = 0; i < size; ++i) {
			list.add(camcSequence.nextStr());
		}
		Map<String, Object> data = new HashMap<>();
		data.put("query_count", Long.toString(seqSynchronizer.getQueryCounter()));
		data.put("update_count", Long.toString(seqSynchronizer.getUpdateCounter()));
		data.put("seq", list);
		return R.data(data);
	}
	
	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "codeName", value = "模块名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "tableName", value = "表名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "modelName", value = "实体名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入code")
	public R<IPage<Code>> list(@ApiIgnore @RequestParam Map<String, Object> code, Query query) {
		IPage<Code> pages = codeService.page(Condition.getPage(query), Condition.getQueryWrapper(code, Code.class));
		return R.data(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增或修改", notes = "传入code")
	public R submit(@Valid @RequestBody Code code) {
		return R.status(codeService.submit(code));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(codeService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 复制
	 */
	@PostMapping("/copy")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "复制", notes = "传入id")
	public R copy(@ApiParam(value = "主键", required = true) @RequestParam Long id) {
		Code code = codeService.getById(id);
		code.setId(null);
		code.setCodeName(code.getCodeName() + "-copy");
		return R.status(codeService.save(code));
	}

	/**
	 * 代码生成
	 */
	@PostMapping( value="/gen-code" , headers="Accept=application/octet-stream" )
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "代码生成", notes = "传入ids")
	public void genCode(@ApiParam(value = "主键集合", required = true) @RequestParam String ids, @ApiParam(value = "页面类型", required = false , defaultValue = "saber") @RequestParam(defaultValue = "saber") String system ,
			@ApiParam(value = "是否生成项目配置", required = false , defaultValue = "true") @RequestParam(defaultValue = "true") boolean genProject ,
			@ApiParam(value = "端口号，当生成项目配置时生效", required = false , defaultValue = "9001" ) @RequestParam(defaultValue = "9001") Integer port  ,
			HttpServletResponse response ) {
		List<Code> codes = codeService.listByIds(Func.toLongList(ids));
		if( codes.isEmpty() )
		{
            log.error( "代码生成需要的配置不全" );  
			return ;
		}
		Datasource datasource = datasourceService.getById(codes.get(0).getDatasourceId());
		CodeGeneratorUtils.generator( codes , 9001 , datasource, response, genProject, system);
		//return R.success("代码生成成功");
	}

}
