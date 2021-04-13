package com.chinasofti.testing.runner;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.chinasofti.core.tool.jackson.JsonUtil;
import com.chinasofti.testing.core.assertr.AssertClass;
import com.chinasofti.testing.core.assertr.AssertConfiguration;
import com.chinasofti.testing.core.definiton.TestProject;
import com.chinasofti.testing.core.exception.AssertDefinitionException;
import com.chinasofti.testing.core.runner.TestCaseRunner2;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;

class TestRunner {
	
	@Test
	void testRun() throws AssertDefinitionException
	{
		String filePath = null;
        URL fileURL = this.getClass().getClassLoader().getResource("test.json");
        if (fileURL != null) {
            filePath = fileURL.getPath();
        }

        String json = FileUtil.readUtf8String(new File(filePath) );
        System.out.println( json );
        
        TestProject project = JsonUtil.parse( json , TestProject.class );
        System.out.println( "project : " + project.toString() );
        
        AssertConfiguration assertConfiguration = new AssertConfiguration();
        AssertClass assertClass = assertConfiguration.assertClass(); 
		
        TestCaseRunner2.getRunner( "D:/report" , project ,assertClass ).run();
	}
	
	@Test
	void testJson() throws JsonMappingException, JsonProcessingException {
		String filePath = null;
        URL fileURL = this.getClass().getClassLoader().getResource("test.json");
        if (fileURL != null) {
            filePath = fileURL.getPath();
        }

        String json = FileUtil.readUtf8String(new File(filePath) );
        //System.out.println( json );
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        //mapper.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
        //mapper.findAndRegisterModules();
		//失败处理
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//单引号处理
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		//反序列化时，属性不存在的兼容处理s
        //mapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
        TestProject project = mapper.readValue( json , TestProject.class  );
        //TestProject project = JsonUtil.parse( json , TestProject.class );
        System.out.println( project.getName() + "  " + UUID.randomUUID().toString(true) );
        System.out.println( project.getSuits().get(0).getTestCases().get(1).getAsserts().toString());
	}

}
