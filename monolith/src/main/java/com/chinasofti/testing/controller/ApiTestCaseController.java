
package com.chinasofti.testing.controller;

import cn.hutool.core.util.RandomUtil;
import com.chinasofti.testing.dto.*;
import com.chinasofti.testing.entity.Report;
import com.chinasofti.testing.service.IApiModuleService;
import com.chinasofti.testing.service.IReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import com.chinasofti.core.mp.support.Condition;
import com.chinasofti.core.mp.support.Query;
import com.chinasofti.core.secure.utils.SecureUtil;
import com.chinasofti.core.serialnumber.Sequence;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.jackson.JsonUtil;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.core.tool.utils.StringUtil;

import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinasofti.testing.core.props.RestTestProperties;
import com.chinasofti.testing.entity.ApiModule;
import com.chinasofti.testing.entity.ApiTestCase;
import com.chinasofti.testing.vo.ApiTestCasePage;
import com.chinasofti.testing.service.IApiTestCaseService;
import com.chinasofti.core.boot.ctrl.BootController;

import java.time.LocalDateTime;

/**
 * 控制器
 *
 * @author Arvin
 * @since 2021-02-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("testing/apiTestCase")
@Api(value = "apiTestCase", tags = "apiTestCase接口")
public class ApiTestCaseController extends BootController {

    private IApiTestCaseService apiTestCaseService;

    private IApiModuleService apiModuleService;

    private Sequence<Long> camcSequence;

    private RestTestProperties restTestProperties;


    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入id")
    public R<DetailApiTestCaseResponse> detail(@PathVariable("id") Long id) {
        ApiTestCase detail = apiTestCaseService.getById(id);
        if (detail == null) {
            return R.fail("not found the data");
        }
        DetailApiTestCaseResponse detailApiTestCaseResponse = new DetailApiTestCaseResponse();
        detailApiTestCaseResponse.setId(detail.getId());
        detailApiTestCaseResponse.setDescription(detail.getDescription());
        detailApiTestCaseResponse.setName(detail.getName());
        detailApiTestCaseResponse.setProjectId(detail.getProjectId());
        if (StringUtil.isNotBlank(detail.getRequest()))
            detailApiTestCaseResponse.setRequest(JsonUtil.parse(detail.getRequest(), HttpRequestElement.class));
        detailApiTestCaseResponse.setNum(detail.getNum());
        detailApiTestCaseResponse.setFolderId(detail.getFolderId());
        detailApiTestCaseResponse.setModuleId(detail.getApiModuleId());
        return R.data(detailApiTestCaseResponse);
    }


    /**
     * 自定义分页
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "api case 分页查询", notes = "传入apiTestCaseQuery")
    public R<IPage<ApiTestCasePage>> page(ApiTestCaseQuery apiTestCaseQuery, Query query) {
        IPage<ApiTestCasePage> pages = apiTestCaseService.selectApiTestCasePage(Condition.getPage(query), apiTestCaseQuery);
        return R.data(pages);
    }

    /**
     * 新增
     */
    @PostMapping("/create")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增", notes = "传入SaveApiTestCaseRequest")
    public R create(@Valid @RequestBody SaveApiTestCaseRequest saveApiTestCaseRequest) {
        ApiTestCase apiTestCase = new ApiTestCase();
        apiTestCase.setDescription(saveApiTestCaseRequest.getDescription());
        apiTestCase.setName(saveApiTestCaseRequest.getName());
        apiTestCase.setProjectId(saveApiTestCaseRequest.getProjectId());
        apiTestCase.setApiModuleId(saveApiTestCaseRequest.getModuleId());
        apiTestCase.setFolderId(saveApiTestCaseRequest.getFolderId());
        apiTestCase.setNum(camcSequence.nextStr());
        apiTestCase.setStatus(ApiTestCase.STATUS_PREPARE);
        if (saveApiTestCaseRequest.getRequest() != null)
            apiTestCase.setRequest(JsonUtil.toJson(saveApiTestCaseRequest.getRequest()));
        return R.status(apiTestCaseService.save(apiTestCase));
    }

    /**
     * 新增或更新
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增或更新", notes = "传入SaveApiTestCaseRequest")
    public R save(@Valid @RequestBody SaveApiTestCaseRequest saveApiTestCaseRequest) {
        ApiTestCase apiTestCase = new ApiTestCase();
        apiTestCase.setId(saveApiTestCaseRequest.getId());
        apiTestCase.setDescription(saveApiTestCaseRequest.getDescription());
        apiTestCase.setName(saveApiTestCaseRequest.getName());
        apiTestCase.setProjectId(saveApiTestCaseRequest.getProjectId());
        apiTestCase.setApiModuleId(saveApiTestCaseRequest.getModuleId());
        apiTestCase.setFolderId(saveApiTestCaseRequest.getFolderId());
        apiTestCase.setStatus(ApiTestCase.STATUS_PREPARE);
        if (saveApiTestCaseRequest.getRequest() != null)
            apiTestCase.setRequest(JsonUtil.toJson(saveApiTestCaseRequest.getRequest()));
        if (saveApiTestCaseRequest.getId() == null)
            apiTestCase.setNum(camcSequence.nextStr());
        return R.status(apiTestCaseService.saveOrUpdate(apiTestCase));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "更新", notes = "传入SaveApiTestCaseRequest")
    public R update(@Valid @RequestBody SaveApiTestCaseRequest saveApiTestCaseRequest) {
        if (saveApiTestCaseRequest.getId() == null)
            return R.fail("cannot found ths id in the request");
        ApiTestCase apiTestCase = new ApiTestCase();
        apiTestCase.setId(saveApiTestCaseRequest.getId());
        apiTestCase.setDescription(saveApiTestCaseRequest.getDescription());
        apiTestCase.setName(saveApiTestCaseRequest.getName());
        apiTestCase.setProjectId(saveApiTestCaseRequest.getProjectId());
        apiTestCase.setApiModuleId(saveApiTestCaseRequest.getModuleId());
        apiTestCase.setFolderId(saveApiTestCaseRequest.getFolderId());
        if (saveApiTestCaseRequest.getRequest() != null)
            apiTestCase.setRequest(JsonUtil.toJson(saveApiTestCaseRequest.getRequest()));
        return R.status(apiTestCaseService.updateById(apiTestCase));
    }

    /**
     * 删除
     */
    @DeleteMapping("/remove")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(apiTestCaseService.deleteLogic(Func.toLongList(ids)));
    }

    /**
     * 测试
     */
    @PostMapping("/run")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "测试执行", notes = "传入ids")
    public R<Long> run(@Valid @RequestBody ApiTestCaseRunRequest apiTestCaseRunRequest) {
    	/*Long reportId= apiTestCaseService.putReportId();*/
        Long reportId=RandomUtil.randomLong(1, 9999999999999L) ;
        apiTestCaseService.run(apiTestCaseRunRequest, SecureUtil.getUser(), restTestProperties);
        return R.data(reportId);
    }

   /* public Long creatReportId() {
        Long reportIdi = 1L;
        return reportIdi;
    }*/


    /**
     * 复制
     */
    @GetMapping("/copy/{id}")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "复制", notes = "将API Module转换到API TestCase")
    public R<DetailApiModuleResponse> copy(@PathVariable("id") Long id) {
        ApiModule apiModule = apiModuleService.getById(id);
        if (apiModule == null) {
            return R.fail("not found the data");
        }

        ApiTestCase apiTestCase = new ApiTestCase();
        apiTestCase.setName(apiModule.getName());
        apiTestCase.setProjectId(apiModule.getProjectId());
        apiTestCase.setNum(camcSequence.nextStr());
        apiTestCase.setFolderId(apiModule.getId());
        apiTestCase.setIsDeleted(apiModule.getIsDeleted());
        apiTestCase.setStatus(apiModule.STATUS_TESTING);
        apiTestCase.setApiModuleId(apiModule.getId());
        if (apiModule.getRequest() != null)
            apiTestCase.setRequest(JsonUtil.toJson(apiModule.getRequest()));
        return R.status(apiTestCaseService.save(apiTestCase));
    }

    /**
     * 测试
     *
     * @param apiTestCaseRunOne
     */
    @PostMapping("/run1")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "测试", notes = "传入id")
    public R<Long> run1(@RequestBody ApiTestCaseRunOne apiTestCaseRunOne) {
        Long reportId = apiTestCaseService.run1(apiTestCaseRunOne, SecureUtil.getUser(), restTestProperties);
        return R.data(reportId);
    }
}
