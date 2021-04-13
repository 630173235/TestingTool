package com.chinasofti.testing.core.assertr;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinasofti.core.tool.utils.StringUtil;
import com.chinasofti.testing.core.definiton.Assert;
import com.chinasofti.testing.core.definiton.AssertParameter;
import com.chinasofti.testing.core.definiton.AssertTemplate;
import com.chinasofti.testing.core.definiton.TestAssert;
import com.chinasofti.testing.core.definiton.TestAssertParameter;
import com.chinasofti.testing.core.exception.AssertRunException;
import com.jayway.jsonpath.JsonPath;
import okhttp3.Response;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class AssertClass {

	private  Map<String,Method> assertMethod = new HashMap<String,Method>();
	
	private  Map<String,Class<?>> assertType = new HashMap<String,Class<?>>();
	
	private  List<AssertTemplate> assertTemplates = new ArrayList<AssertTemplate>();
	
	protected final static Logger logger = LoggerFactory.getLogger(AssertClass.class);
	 
	public void appendAssertTemplate( AssertTemplate assertTemplate )
	{
		assertTemplates.add( assertTemplate );
	}
	
	public Map<String, Method> getAssertMethod() {
		return assertMethod;
	}

	public void setAssertMethod(Map<String, Method> assertMethod) {
		this.assertMethod = assertMethod;
	}

	public Map<String, Class<?>> getAssertType() {
		return assertType;
	}

	public void setAssertType(Map<String, Class<?>> assertType) {
		this.assertType = assertType;
	}

	public List<AssertTemplate> getAssertTemplates() {
		return assertTemplates;
	}

	public void setAssertTemplates(List<AssertTemplate> assertTemplates) {
		this.assertTemplates = assertTemplates;
	}

	public class AssertResult
	{
	    public boolean result;
	    
	    public List<String> desc = new ArrayList<String>();
	    
	    public void append( String desc )
	    {
	    	this.desc.add( desc );
	    }
	}
	
	protected void appendAssertType( String key , Class<?> clazz )
	{
	    assertType.putIfAbsent( key, clazz );	
	}
	
	protected void appendAssertMethod( String key , Method value )
	{
		assertMethod.put( key , value );
	}
	
	protected boolean isEmpty()
	{
		return this.assertMethod.isEmpty();
	}
	
	protected Method getMethod( String key )
	{
		return this.assertMethod.get( key );
	}
	
	private void checkParam( String type, String methodName , String methodCode ,TestAssertParameter param ) throws AssertRunException
	{
		List<AssertParameter> params = null;
		loop: for( AssertTemplate assertTemplate : assertTemplates )
		{
			if( assertTemplate.getName().equals( type ) )
			{
				for( Assert assertt : assertTemplate.getAsserts() )
				{
					if( assertt.getId().equals( methodCode ) && assertt.getMethod().equals( methodName ) )
					{
						params = assertt.getParams();
						break loop;
					}
						
				}
			}
		}
		
		if( params == null || params.isEmpty() )
			throw new AssertRunException( type +"."+ methodName+"."+ methodCode+"(" + param.toString()  +")"  , "cannot found the parameter" );
		
		for( AssertParameter parameter : params )
		{
				
			if( parameter.getIndex() == param.getIndex() && parameter.getName().equals( param.getName() )
					|| StringUtil.isBlank( param.getType() )?true : parameter.getType().equals( param.getType() ) )
				return;
		}
		
		throw new AssertRunException( type +"."+ methodName+"."+ methodCode+"(" + param.toString()  +")"  , "cannot found the parameter" );
	}
	
	public AssertResult execute( List<TestAssert> asserts , Response response ) throws AssertRunException 
	{
		AssertResult assertResult = new AssertResult();
		assertResult.result = Boolean.TRUE;
		for( TestAssert testAssert : asserts )
		{
			Map<Boolean,String> tmp = this.execute( testAssert , response );
			assertResult.append( tmp.get( Boolean.TRUE ) != null ? tmp.get( Boolean.TRUE ) : tmp.get( Boolean.FALSE ) );
			if( tmp.get( Boolean.FALSE ) != null )
				assertResult.result = Boolean.FALSE;
		}
		return assertResult;
	}
	
	public Map<Boolean,String> execute(TestAssert testAssert , Response response ) throws AssertRunException
	{
		Class clazz = assertType.get(  testAssert.getType() );
		if( clazz == null )
			throw new AssertRunException( testAssert.getType()  , "cannot found the assert class" );
		Method method = assertMethod.get( testAssert.getType() + "." + testAssert.getName() + "." + testAssert.getMethod() );
		if( method == null )
		    throw new AssertRunException(  testAssert.getType() + "." + testAssert.getName() + "." + testAssert.getMethod() , "cannot found the assert method" );
		List<TestAssertParameter> params = testAssert.getParams();
		if( params.isEmpty() )
			throw new AssertRunException(  testAssert.getType() + "." + testAssert.getName() + "." + testAssert.getMethod() , "cannot found any parameter" );
		params.stream().sorted(Comparator.comparing(TestAssertParameter::getIndex)).collect(Collectors.toList());
		
		Object[] obj = new Object[params.size()];
		StringBuilder sb = new StringBuilder();
		for( int i = 0 ; i < params.size() ; i++  )
		{
			checkParam(   testAssert.getType() , testAssert.getName()  , testAssert.getMethod() , params.get( i ) );
			String param = params.get( i ).getValue().toString();
			if( param.startsWith( "@" ) )
			{
				param = param.substring( 1 , param.length() );
				switch( param )
				{
				    case "statusCode":
				    	obj[i] = response.code();
				    	break;
				    case "body":
				    	try {
							obj[i] = response.body().string();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							throw new AssertRunException( "cannot get the response message." );
						}
				    	break;
				    default:
				        obj[i] = response.headers().get( param );
			    	    break;
				}
			}
			else if( param.startsWith( "^" )  )
			{
				param = param.substring( 1 , param.length() );
				try {
					logger.debug( param );
					String json = response.body().string();
					obj[i] = JsonPath.read( json , param );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new AssertRunException( "cannot get the response message." );
				}
			}
			else 
			{
				//obj[i] =  Integer.parseInt( params.get( i ).getValue().toString() );
				obj[i] =  params.get( i ).getValue();
			}
			
			sb.append( params.get( i ).getName() + " = " + obj[i].toString() + "," );
			logger.debug(  params.get( i ).getName() + " = " + obj[i].toString() );
		}
		
		try {
			Boolean result = (Boolean) method.invoke(clazz.newInstance(), obj);
			Map<Boolean,String> resultMap = new HashMap<Boolean,String>();
			String desc = "[result = " + result.toString() + "," + (sb.toString().endsWith(",")?sb.toString().substring( 0 , sb.toString().length() -1 ): sb.toString()) + "]";
			logger.debug( desc );
			resultMap.put( result, desc);
			return resultMap;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new AssertRunException( testAssert.getType() + "." + testAssert.getName() + "." + testAssert.getMethod()  , "cannot invoke the assert" );
		}
	}
}
