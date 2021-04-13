package com.chinasofti.develop.utils;

import java.util.Properties;
import org.apache.velocity.app.Velocity;
import com.chinasofti.core.tool.constant.BootConstant;

/**
 * VelocityEngine工厂
 * 
 * 
 */
public class VelocityInitializer
{
    
   
	/**
     * 初始化vm方法
     */
    public static void initVelocity()
    {
        Properties p = new Properties();
        try
        {
            // 加载classpath目录下的vm文件        	
            p.setProperty( "resource.loader" , "globbing,string,file" );
        	p.setProperty( "globbing.resource.loader.class" , "com.chinasofti.develop.utils.StructuredGlobbingResourceLoader" );
        	p.setProperty( "string.resource.loader.class" , "org.apache.velocity.runtime.resource.loader.StringResourceLoader" );
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // 定义字符集
            p.setProperty(Velocity.INPUT_ENCODING, BootConstant.UTF_8);
            p.setProperty(Velocity.OUTPUT_ENCODING, BootConstant.UTF_8);
            
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
