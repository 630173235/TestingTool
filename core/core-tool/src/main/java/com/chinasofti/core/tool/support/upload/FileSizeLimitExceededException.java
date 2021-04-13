package com.chinasofti.core.tool.support.upload;

/**
 * 文件名大小限制异常类
 * 
 * 
 */
public class FileSizeLimitExceededException extends FileException
{
	 public FileSizeLimitExceededException(long defaultMaxSize)
	 {
	        super("upload.exceed.maxSize : " + defaultMaxSize );
	 }

	private static final long serialVersionUID = 1L;
}
