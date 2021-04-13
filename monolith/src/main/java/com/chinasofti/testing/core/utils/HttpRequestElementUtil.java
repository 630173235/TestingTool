package com.chinasofti.testing.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinasofti.testing.core.enums.HttpType;
import com.chinasofti.testing.dto.HttpRequestElement;
import com.chinasofti.testing.dto.KeyValue;

public class HttpRequestElementUtil {

	public static Map<String,String> toHeaderMap(HttpRequestElement request)
	{
		List<KeyValue> headerList = request.getHeaders();
		Map<String,String> headerMap = new HashMap<String,String>();
		if( headerList != null )
		{
			for(  KeyValue kv : headerList )
			{
				headerMap.put( kv.getName(), kv.getValue() );
			}
		}
		return headerMap;
	}
	
	public static HttpType toHttpType( String method )
	{
		if( method.toUpperCase().equals( "GET" ) )  return HttpType.GET;
		if( method.toUpperCase().equals( "POST" ) )  return HttpType.POST;
		if( method.toUpperCase().equals( "PUT" ) )  return HttpType.PUT;
		if( method.toUpperCase().equals( "DELETE" ) )  return HttpType.DELETE;
		return null;
	}
	
	public static Map<String,Object> toQueryParams(HttpRequestElement request)
	{

		List<KeyValue> argumentList = request.getArguments();
		Map<String,Object> argumentMap = new HashMap<String,Object>();
		if( argumentList != null )
		{
			for(  KeyValue kv : argumentList )
			{
				argumentMap.put( kv.getName(), kv.getValue() );
			}
		}
		return argumentMap;
	
	}
	
	public static List<String> toRestParams(HttpRequestElement request)
	{
		List<KeyValue> restList = request.getRest();
		List<String> pathParams = new ArrayList<String>();
		if( restList != null )
		{
			for(  KeyValue kv : restList )
			{
				pathParams.add( kv.getValue() );
			}
		}
		return pathParams;
	}
}
