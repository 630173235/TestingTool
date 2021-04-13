package com.chinasofti.core.tool.support.upload;

import org.apache.commons.lang3.StringUtils;

import com.chinasofti.core.tool.api.IResultCode;
import com.chinasofti.core.tool.api.ResultCode;

import lombok.Getter;

/**
 * 文件信息异常类
 * 
 * 
 */
public class FileException extends RuntimeException
{
	@Getter
	private final IResultCode resultCode;

	public FileException(String message) {
		super(message);
		this.resultCode = ResultCode.FAILURE;
	}

	public FileException(IResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}

	public FileException(IResultCode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}

	/**
	 * 提高性能
	 *
	 * @return Throwable
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	public Throwable doFillInStackTrace() {
		return super.fillInStackTrace();
	}

}
