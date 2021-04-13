
package com.chinasofti.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import com.chinasofti.core.boot.ctrl.BootController;
import com.chinasofti.core.launch.constant.AppConstant;
import com.chinasofti.core.mp.support.Condition;
import com.chinasofti.core.mp.support.Query;
import com.chinasofti.core.secure.BootUser;
import com.chinasofti.core.secure.utils.SecureUtil;
import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.system.entity.Post;
import com.chinasofti.system.service.IPostService;
import com.chinasofti.system.vo.PostVO;
import com.chinasofti.system.wrapper.PostWrapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 岗位表 控制器
 *
 *  @author Arvin Zhou
 */
@RestController
@AllArgsConstructor
@RequestMapping( AppConstant.APPLICATION_SYSTEM_NAME + "/post")
@Api(value = "岗位表", tags = "岗位表接口")
public class PostController extends BootController {

	private IPostService postService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入post")
	public R<PostVO> detail(Post post) {
		Post detail = postService.getOne(Condition.getQueryWrapper(post));
		return R.data(PostWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 岗位表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入post")
	public R<IPage<PostVO>> list(Post post, Query query) {
		IPage<Post> pages = postService.page(Condition.getPage(query), Condition.getQueryWrapper(post));
		return R.data(PostWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 岗位表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入post")
	public R<IPage<PostVO>> page(PostVO post, Query query) {
		IPage<PostVO> pages = postService.selectPostPage(Condition.getPage(query), post);
		return R.data(pages);
	}

	/**
	 * 新增 岗位表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入post")
	public R save(@Valid @RequestBody Post post) {
		return R.status(postService.save(post));
	}

	/**
	 * 修改 岗位表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入post")
	public R update(@Valid @RequestBody Post post) {
		return R.status(postService.updateById(post));
	}

	/**
	 * 新增或修改 岗位表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入post")
	public R submit(@Valid @RequestBody Post post) {
		post.setTenantId(SecureUtil.getTenantId());
		return R.status(postService.saveOrUpdate(post));
	}


	/**
	 * 删除 岗位表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(postService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "下拉数据源", notes = "传入post")
	public R<List<Post>> select(String tenantId, BootUser bladeUser) {
		List<Post> list = postService.list(Wrappers.<Post>query().lambda().eq(Post::getTenantId, Func.toStr(tenantId, bladeUser.getTenantId())));
		return R.data(list);
	}

}
