package com.chinasofti.develop.support;

import org.junit.jupiter.api.Test;
import com.chinasofti.core.tool.utils.Func;

import cn.hutool.core.util.ZipUtil;

class TestCodeGenerator {

	@Test
	void testZipFile()
	{	
		CodeGenerator generator = new CodeGenerator();
		// 设置数据源
		generator.setDriverName( "com.mysql.cj.jdbc.Driver" );
		generator.setUrl( "jdbc:mysql://127.0.0.1:3306/monolith?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true" );
		generator.setUsername( "root" );
		generator.setPassword( "zx883408" );
		generator.setPort( 9001 );
		// 设置基础配置
		generator.setSystemName( "saber" );
		generator.setServiceName( "micro-hr" );
		generator.setPackageName( "com.chinasofti.testing");
		generator.setPackageDir( "d:\\root\\code\\api" );
		generator.setPackageWebDir( "d:\\root\\code\\web" );
		generator.setTablePrefix(Func.toStrArray( "testing_" ));
		generator.setIncludeTables(Func.toStrArray( "testing_case_folder,testing_report,testing_api_test_result" ));
		// 设置是否继承基础业务字段
		generator.setHasSuperEntity( Boolean.FALSE );
		// 设置是否开启包装器模式
		generator.setHasWrapper( Boolean.TRUE );
		generator.runCfg( );
		generator.runCode( );
		
		//ZipUtil.zip( generator.getPackageDir() , "d:\\code\\api.zip" );
		
		//generator.cleanHistory();
	
	}
	
	@Test
	void testBledeCFGGenerator()
	{
		CodeGenerator generator = new CodeGenerator();
		generator.setServiceName( "soft" );
		generator.setPort( 9001 );
		generator.setPackageDir( "d:\\test\\soft" );
		generator.runCfg( );
	}
	
	@Test
	void testBladeCodeGenerator() {
		
		CodeGenerator generator = new CodeGenerator();
		// 设置数据源
		generator.setDriverName( "com.mysql.cj.jdbc.Driver" );
		generator.setUrl( "jdbc:mysql://127.0.0.1:3306/blade?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true" );
		generator.setUsername( "root" );
		generator.setPassword( "zx883408" );
		// 设置基础配置
		generator.setSystemName( "saber" );
		generator.setServiceName( "chinasofti-demo" );
		generator.setPackageName( "com.chinasofti.demo");
		generator.setPackageDir( "d:\\root\\code\\api" );
		generator.setPackageWebDir( "d:\\root\\code\\web" );
		generator.setTablePrefix(Func.toStrArray( "chinasofti_" ));
		generator.setIncludeTables(Func.toStrArray( "chinasofti_demo" ));
		// 设置是否继承基础业务字段
		generator.setHasSuperEntity( true );
		// 设置是否开启包装器模式
		generator.setHasWrapper( true );
		
		generator.runCode( );
		
		
	}

}
