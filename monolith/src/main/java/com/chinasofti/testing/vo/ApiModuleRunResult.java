package com.chinasofti.testing.vo;

import java.io.Serializable;
import java.util.List;

public class ApiModuleRunResult implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -5325720799721465699L;

	private String beginTime;
	 
	 private List<String> throwableLog;
	 
	 private int statusCode;
	 
	 public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int status) {
		this.statusCode = status;
	}

	public List<String> getThrowableLog() {
		return throwableLog;
	}

	public void setThrowableLog(List<String> throwableLog) {
		this.throwableLog = throwableLog;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	private String totalTime;
}
