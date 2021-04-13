package com.chinasofti.develop.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.develop.entity.Code;
import com.chinasofti.develop.entity.Datasource;
import com.chinasofti.develop.support.CodeGenerator;

import cn.hutool.core.util.ZipUtil;

public class CodeGeneratorUtils {

	public static void generator( List<Code> codes ,int port , Datasource datasource , HttpServletResponse response , boolean genProject , String system )
	{
		String _system = (system == null ? "saber" : system);
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		CodeGenerator generatorCFG = new CodeGenerator();
		
		synchronized ( codes.get( 0 ).getApiPath() )
		{
			generatorCFG.setServiceName( codes.get( 0 ).getServiceName() );
			generatorCFG.setPort( port );
			generatorCFG.setPackageDir(codes.get( 0 ).getApiPath());
			generatorCFG.setPackageWebDir(codes.get( 0 ).getWebPath());
			generatorCFG.setPackageName( codes.get( 0 ).getPackageName() );
			if( genProject == true )
			{
				generatorCFG.runCfg();
			}
			
			codes.forEach(code -> {
				CodeGenerator generator = new CodeGenerator();
				// 设置数据源
				generator.setDriverName(datasource.getDriverClass());
				generator.setUrl(datasource.getUrl());
				generator.setUsername(datasource.getUsername());
				generator.setPassword(datasource.getPassword());
				// 设置基础配置
				generator.setSystemName(_system);
				generator.setServiceName(code.getServiceName());
				generator.setPackageName(code.getPackageName());
				generator.setPackageDir(code.getApiPath());
				generator.setPackageWebDir(code.getWebPath());
				generator.setTablePrefix(Func.toStrArray(code.getTablePrefix()));
				generator.setIncludeTables(Func.toStrArray(code.getTableName()));
				// 设置是否继承基础业务字段
				generator.setHasSuperEntity(code.getBaseMode() == 2);
				// 设置是否开启包装器模式
				generator.setHasWrapper(code.getWrapMode() == 2);
				
				generator.runCode();
			});
			
			ZipUtil.zip( byteOutputStream , Charset.forName( "UTF-8" )  , true 
					, new FileFilter() {

						@Override
						public boolean accept(File arg0) {
							// TODO Auto-generated method stub
							return true;
						}
				
			       },
				   new File(codes.get( 0 ).getApiPath()) );
			
			generatorCFG.cleanHistory();
		}
		
		response.setContentType("application/octet-stream");
		//response.setCharacterEncoding(Charsets.UTF_8.name());
		String fileName = "code";
		try {
			fileName = URLEncoder.encode( codes.get( 0 ).getServiceName() , Charsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".zip");
			IOUtils.write(byteOutputStream.toByteArray(), response.getOutputStream());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				byteOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
