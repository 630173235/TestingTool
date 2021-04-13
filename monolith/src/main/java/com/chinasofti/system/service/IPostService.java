
package com.chinasofti.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinasofti.core.mp.base.BaseService;
import com.chinasofti.system.entity.Post;
import com.chinasofti.system.vo.PostVO;

import java.util.List;

/**
 * 岗位表 服务类
 *
 *  @author Arvin Zhou
 */
public interface IPostService extends BaseService<Post> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param post
	 * @return
	 */
	IPage<PostVO> selectPostPage(IPage<PostVO> page, PostVO post);

	/**
	 * 获取岗位ID
	 *
	 * @param tenantId
	 * @param postNames
	 * @return
	 */
	String getPostIds(String tenantId, String postNames);

	/**
	 * 获取岗位名
	 *
	 * @param postIds
	 * @return
	 */
	List<String> getPostNames(String postIds);

}
