package com.chinasofti.develop.service;

import com.chinasofti.develop.entity.Application;
import com.chinasofti.develop.entity.Datasource;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 应用生成表 服务类
 *
 * @author Micro
 * @since 2020-12-17
 */
public interface IApplicationService extends IService<Application> {

	public boolean createTable( Application application , Datasource dataSource ); 
}
