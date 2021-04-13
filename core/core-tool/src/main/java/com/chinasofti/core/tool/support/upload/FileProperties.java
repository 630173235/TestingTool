
package com.chinasofti.core.tool.support.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Arvin Zhou
 */
@Component
@ConfigurationProperties("boot.file")
public class FileProperties {

	private static String allow = "xls,xlsx,csv,txt";

	private static String base = "";

	public void setAllow( String allow )
	{
		FileProperties.allow = allow;
	}
	
	public void setBase( String base )
	{
		FileProperties.base = base;
	}
	
	public static String getAllow()
	{
		return allow;
	}
	
	public static String getBase()
	{
		return base;
	}
	
	/**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getBase() + "/upload";
    }
}
