package com.chinasofti.testing.core.utils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinasofti.core.tool.utils.StringUtil;
import com.chinasofti.testing.core.definiton.TestStep;
import com.chinasofti.testing.core.enums.ContentType;

import java.io.IOException;
import java.util.Map;


public class HttpUtils {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    public static MediaType JSON = MediaType.parse("application/json");
	public static MediaType XML = MediaType.parse("application/xml");
	public static String COOKIE = "Cookie";
	
    private OkHttpClient okHttpClient;

    private String baseUrl;
    
    /**
     * 构造方法
     */
    public HttpUtils( String baseUrl ) {
    	okHttpClient = new OkHttpClient();
    	this.baseUrl = baseUrl;
    }


    /**
     * 获取本次请求的URL，携带参数
     *
     * @param path
     * @param params
     */
    private String getRequestInfo(String path, Map<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : params.keySet()) {
            stringBuilder.append(key).append("=").append(params.get(key)).append("&");
        }

        if (stringBuilder.length() >= 1 && stringBuilder.toString().endsWith("&")) {
            stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.length() - 1));
        }

        return path + "?" + stringBuilder;
    }

    /**
     * 获取本次请求的响应信息
     *
     * @param response
     */
    private String getResponseInfo(Response response) {
    	return "[" + response.code() + "]" + response.body().toString();
    }
    
    private String buildPath( String path , Map<String,Object> params )
    {
    	StringBuffer sb = new StringBuffer();
		if (params != null && params.keySet().size() > 0) {
			params.forEach((k, v) -> sb.append(k).append("=").append(v).append("&"));
			return this.baseUrl ==null?"":this.baseUrl + path + "?" + sb.toString().substring( 0 , sb.toString().length() - 1 );
		}
		else
			return this.baseUrl ==null?"":this.baseUrl + path;
    }
    
    private Request.Builder buildHeader( Map<String,String> headers ,Map<String, String> cookies )
    {
    	Request.Builder builder = new Request.Builder();
		if (headers != null && headers.keySet().size() > 0) {
			headers.forEach(builder::addHeader);
		}
		StringBuffer sb = new StringBuffer();
		if (cookies != null && cookies.keySet().size() > 0) 
		{
			cookies.forEach( (k,v) -> sb.append(k).append("=").append(v).append(";"));
			builder.addHeader( COOKIE , sb.toString().substring( 0 , sb.toString().length() - 1 ) );
		}
		return builder;
    }
    
    private Response post( String path , Map<String,String> headers , Map<String, String> cookies , Map<String,Object> params , String body , MediaType mediaType ) throws IOException
    {
    	String _path = buildPath( path , params );
    	Request.Builder builder = buildHeader( headers , cookies );
    	RequestBody requestBody = RequestBody.create(mediaType, body==null?"":body);
    	Request request = builder.url(_path).post( requestBody ).build();
    	return okHttpClient.newCall(request).execute();
    }
    
    private Response delete( String path , Map<String,String> headers , Map<String, String> cookies , Map<String,Object> params , String body , MediaType mediaType ) throws IOException
    {
    	String _path = buildPath( path , params );
    	Request.Builder builder = buildHeader( headers , cookies );
    	Request request = null;
    	if( StringUtil.isNotBlank( body ))
    	{
    		RequestBody requestBody = RequestBody.create(mediaType, body);
        	request = builder.url(_path).delete( requestBody ).build();
    	}
    	else
    		request = builder.url(_path).delete().build();
    	return okHttpClient.newCall(request).execute();
    }
    
    private Response put( String path , Map<String,String> headers , Map<String, String> cookies , Map<String,Object> params , String body , MediaType mediaType ) throws IOException
    {
    	String _path = buildPath( path , params );
    	Request.Builder builder = buildHeader( headers , cookies );
    	RequestBody requestBody = RequestBody.create(mediaType, body==null?"":body);
    	Request request = builder.url(_path).put( requestBody ).build();
    	return okHttpClient.newCall(request).execute();
    }
    
    private Response post(  TestStep testStep ) throws IOException
    {
    	if( testStep.getContentType() == null )
    		throw new RuntimeException( "post请求未指定Content-Type" );
    	if( testStep.getContentType().equals( ContentType.JSON ) )
    	{
    		return post( testStep.getPath() , testStep.getHeaders() , testStep.getCookies() , testStep.getParams() , testStep.getBody() , JSON );
    	}
    	else
    	{
    		throw new RuntimeException(String.format( "暂不支持%s", testStep.getContentType() ));
    	}
    }
    
    private Response delete( TestStep testStep ) throws IOException
    {
    	if(  testStep.getContentType() == null  )
    		return delete( testStep.getPath() , testStep.getHeaders() , testStep.getCookies() , testStep.getParams() , null , JSON );
    	if( testStep.getContentType().equals( ContentType.JSON ) )
    	{
    		return delete( testStep.getPath() , testStep.getHeaders() , testStep.getCookies() , testStep.getParams() , testStep.getBody() , JSON );
    	}
    	else
    	{
    		throw new RuntimeException(String.format( "暂不支持%s", testStep.getContentType() ));
    	}
    }
    
    private Response put( TestStep testStep ) throws IOException
    {
    	if( testStep.getContentType() == null )
    		throw new RuntimeException( "put请求未指定Content-Type" );
    	if( testStep.getContentType().equals( ContentType.JSON ) )
    	{
    		return put( testStep.getPath() , testStep.getHeaders() , testStep.getCookies() , testStep.getParams() , testStep.getBody() , JSON );
    	}
    	else
    	{
    		throw new RuntimeException(String.format( "暂不支持%s", testStep.getContentType() ));
    	}
    }
    
    private Response get( TestStep testStep ) throws IOException
    {
    	String _path = buildPath( testStep.getPath() , testStep.getParams() );
    	Request.Builder builder = buildHeader( testStep.getHeaders() , testStep.getCookies() );
    	Request request = builder.url(_path).build();
    	return okHttpClient.newCall(request).execute();
    }
    /**
     * 请求
     *
     * @param testStep
     * @throws IOException 
     */
    public Response request(TestStep testStep) throws IOException 
    {        
    	logger.info("[" + testStep.getType().getValue() + "]" + testStep.getPath() );
    	Response response = null;
    	switch(testStep.getType())
    	{
    	   case GET:
                response = get(testStep);
                break;
           case POST:
        	    response = post(testStep);
                break;
           case DELETE:
        	    response = delete(testStep);
             	break;
           case PUT:
        	    response = put(testStep);
                break;
           default:
               throw new RuntimeException(String.format( "暂不支持%s请求类型", testStep.getType() ));
    	}
    	return response;
    }
}

