package com.chinasofti.testing.core.definiton;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.chinasofti.testing.core.enums.ContentType;
import com.chinasofti.testing.core.enums.HttpType;


public class TestStep implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1538996024284933801L;
	/**
     * 请求类型：post get
     */
    private HttpType type;
    
    private ContentType contentType;
    
    /**
     * 接口
     */
    private String path;
    public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	/**
     * 请求携带的参数，key、value格式
     */
    private Map<String, Object> params = new HashMap<>();
    
    private Map<String, String> headers = new HashMap<>();
    
    private Map<String, String> cookies = new HashMap<>();
    
    public void appendHeaders( Map<String, String> headers )
    {
    	if( headers != null && !headers.isEmpty() )
    	    this.headers.putAll( headers );
    }
    
    public void appendCookies( Map<String, String> cookies )
    {
    	if( cookies != null && !cookies.isEmpty() )
    	    this.cookies.putAll( cookies );
    }
    
    /**
     * 消息体
     */
    private String body;

    public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public HttpType getType() {
        return type;
    }

    public void setType(HttpType type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
    	if( params != null && !params.isEmpty() )
            this.params = params;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
