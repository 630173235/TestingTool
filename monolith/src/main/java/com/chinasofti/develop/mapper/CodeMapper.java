
package com.chinasofti.develop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.chinasofti.develop.entity.Code;

/**
 * Mapper 接口
 *
 *  @author Arvin Zhou
 */
@Mapper
public interface CodeMapper extends BaseMapper<Code> {

	List<Code> selectCode( Long applicationId );
}
