package com.chinasofti.testing.core.assertr;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chinasofti.core.tool.utils.StringUtil;
import com.chinasofti.testing.core.annotation.AssertMethodAnnotation;
import com.chinasofti.testing.core.annotation.AssertParameterAnnotation;
import com.chinasofti.testing.core.annotation.AssertTemplateAnnotation;
import com.chinasofti.testing.core.config.RestTestConfiguration;
import com.chinasofti.testing.core.definiton.Assert;
import com.chinasofti.testing.core.definiton.AssertParameter;
import com.chinasofti.testing.core.definiton.AssertTemplate;
import com.chinasofti.testing.core.exception.AssertDefinitionException;

@Configuration
public class AssertConfiguration {

	@Value("${rest-test.testAssert.packagePath}")
    private String assertPath = "com.chinasofti.testing.core.assertr";
	
	protected final static Logger logger = LoggerFactory.getLogger(RestTestConfiguration.class);
	
	@Bean
	public AssertClass assertClass() throws AssertDefinitionException
	{
		AssertClass assertClass = new AssertClass();
		
		Reflections reflections = new Reflections(assertPath);
		Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(AssertTemplateAnnotation.class);
		for (Class<?> clazz : typesAnnotatedWith )
		{
			AssertTemplateAnnotation assertService = clazz.getAnnotation( AssertTemplateAnnotation.class );
			if( StringUtil.isBlank( assertService.name() ) )
				throw new AssertDefinitionException( clazz.getName() + " need a name attribute in annotation(AssertService)" );
			AssertTemplate assertTemplate = new AssertTemplate();
			assertTemplate.setName(  assertService.name() );
			assertClass.appendAssertType( assertService.name() , clazz );
			Method[] methods = clazz.getDeclaredMethods();
			for( Method method : methods )
			{
				if (method.isAnnotationPresent(AssertMethodAnnotation.class))
				{
					AssertMethodAnnotation assertmethod = method.getAnnotation( AssertMethodAnnotation.class );
					if( StringUtil.isBlank( assertmethod.name() ) )
						throw new AssertDefinitionException( clazz.getName() + "." + method.getName() + " need a name attribute in annotation(AssertMethod)" );
					if( StringUtil.isBlank( assertmethod.code() ) )
						throw new AssertDefinitionException( clazz.getName() + "." + method.getName() + " need a code attribute in annotation(AssertMethod)" );
					if( method.getReturnType() == null || ( !method.getReturnType().getCanonicalName().equals( "boolean" ) && 
							!method.getReturnType().getCanonicalName().equals( "java.lang.Boolean" ) ) )
						throw new AssertDefinitionException( clazz.getName() + "." + method.getName() + " must return boolean or java.lang.Boolean" );
					
					assertClass.appendAssertMethod( assertService.name() + "." + assertmethod.name() + "." + assertmethod.code(), method );
					
					Assert  assertt = new Assert();
					assertt.setId(  assertmethod.code()  );
					assertt.setMethod(  assertmethod.name() );
					Parameter[] parameters = method.getParameters();
					for( int i = 0 ; i < parameters.length ; i++ )
					{
						Parameter parameter = parameters[i];
						AssertParameterAnnotation assertParameterAnnotation = parameter.getAnnotation( AssertParameterAnnotation.class );
						
						AssertParameter assertParameter = new AssertParameter();	
						assertParameter.setIndex( i + 1 );
						if( assertParameterAnnotation == null || StringUtil.isBlank( assertParameterAnnotation.name() ) )
							assertParameter.setName( parameter.getName() );
						else
							assertParameter.setName( assertParameterAnnotation.name() );
						assertParameter.setType( parameter.getType().getCanonicalName() );
						assertt.appendParameter( assertParameter );
					}
					assertTemplate.appendAssert( assertt );
					//method.invoke(clazz.newInstance(), requestInfo.getMap());
				}
			}
			assertClass.appendAssertTemplate( assertTemplate );
		}
		
		return assertClass;
	}
}
