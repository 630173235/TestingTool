package com.chinasofti.develop.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.chinasofti.develop.entity.Application;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 应用生成表 Mapper 接口
 *
 * @author Micro
 * @since 2020-12-17
 */
@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {

}
