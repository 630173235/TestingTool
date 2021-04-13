package com.chinasofti.testing.core.assertr;

import java.util.List;

import com.chinasofti.testing.core.annotation.AssertMethodAnnotation;
import com.chinasofti.testing.core.annotation.AssertParameterAnnotation;
import com.chinasofti.testing.core.annotation.AssertTemplateAnnotation;

@AssertTemplateAnnotation( name = "AssertStatus" )
public class AssertStatus {

	@AssertMethodAnnotation( name="equals" , code="equalsInteger" )
	public Boolean equals( @AssertParameterAnnotation( name="expect" ) Integer expect , 
			@AssertParameterAnnotation( name="actual" )  Integer actual )
	{
		  if( expect.intValue() == actual.intValue() )  return true;
          return false;
	}
	
	@AssertMethodAnnotation( name="between" , code="betweenInteger" )
	public Boolean between(  @AssertParameterAnnotation( name="expectMin" ) Integer expectMin , 
			@AssertParameterAnnotation( name="expectMax" ) Integer expectMax , 
			@AssertParameterAnnotation( name="actual" )  Integer actual )
	{
		if( actual >= expectMin && actual <= expectMax )
		    return true;
		return false;
	}
	
	@AssertMethodAnnotation( name="equals" , code="equalsString" )
	public Boolean equals( @AssertParameterAnnotation( name="expect" ) String expect , 
			@AssertParameterAnnotation( name="actual" )  String actual )
	{
		if( expect.equals( actual ) )  return true;
          return false;
	}
	
	@AssertMethodAnnotation( name="equals" , code="equalsJsonContent" )
	public Boolean equals( @AssertParameterAnnotation( name="expect" ) Double expect , 
			@AssertParameterAnnotation( name="actual" )  Object obj )
	{
		Double actual = (Double)obj;
		if(expect.doubleValue() == actual.doubleValue() )  return true;
		return false;
	}
}
