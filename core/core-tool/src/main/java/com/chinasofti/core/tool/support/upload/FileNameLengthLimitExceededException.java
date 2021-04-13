package com.chinasofti.core.tool.support.upload;

import com.chinasofti.core.tool.api.IResultCode;

/**
 * 文件名称超长限制异常类
 * 
 * 
 */
public class FileNameLengthLimitExceededException extends FileException
{
	 public FileNameLengthLimitExceededException(int defaultFileNameLength)
	 {
	        super("upload.filename.exceed.length : " + defaultFileNameLength );
	 }

	private static final long serialVersionUID = 1L;
}
