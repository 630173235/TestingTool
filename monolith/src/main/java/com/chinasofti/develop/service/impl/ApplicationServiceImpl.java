
package com.chinasofti.develop.service.impl;

import com.chinasofti.develop.entity.Application;
import com.chinasofti.develop.entity.Datasource;
import com.chinasofti.develop.mapper.ApplicationMapper;
import com.chinasofti.develop.service.IApplicationService;
import com.chinasofti.develop.support.TableGenerator;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 应用生成表 服务实现类
 *
 * @author Micro
 * @since 2020-12-17
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements IApplicationService {

	@Override
	public boolean createTable(Application application , Datasource dataSource ) {
		// TODO Auto-generated method stub
		try
		{
			this.save( application );
			TableGenerator tableGenerator = new TableGenerator();
			tableGenerator.setDropOldTable( true );
			tableGenerator.setTables( application.getEntitis() );
			tableGenerator.setDriverName(dataSource.getDriverClass());
			tableGenerator.setUrl(dataSource.getUrl());
			tableGenerator.setUsername(dataSource.getUsername());
			tableGenerator.setPassword(dataSource.getPassword());
			tableGenerator.run();
		}
		catch( Throwable t )
		{
			t.printStackTrace();
			return false;
		}
		return true;
	}

}
