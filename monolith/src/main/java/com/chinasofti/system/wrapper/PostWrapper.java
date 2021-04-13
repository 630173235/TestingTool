
package com.chinasofti.system.wrapper;

import com.chinasofti.core.mp.support.BaseEntityWrapper;
import com.chinasofti.core.tool.utils.BeanUtil;
import com.chinasofti.core.tool.utils.SpringUtil;
import com.chinasofti.system.entity.Post;
import com.chinasofti.system.service.IDictService;
import com.chinasofti.system.vo.PostVO;

import java.util.Objects;

/**
 * 岗位表包装类,返回视图层所需的字段
 *
 *  @author Arvin Zhou
 */
public class PostWrapper extends BaseEntityWrapper<Post, PostVO> {

	private static IDictService dictService;

	static {
		dictService = SpringUtil.getBean(IDictService.class);
	}

	public static PostWrapper build() {
		return new PostWrapper();
	}

	@Override
	public PostVO entityVO(Post post) {
		PostVO postVO = Objects.requireNonNull(BeanUtil.copy(post, PostVO.class));
		String categoryName = dictService.getValue("post_category", post.getCategory());
		postVO.setCategoryName(categoryName);
		return postVO;
	}

}
