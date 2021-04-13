package com.chinasofti.develop.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import com.chinasofti.develop.support.CodeGenerator;

public class VelocityUtils
{
    /**
     * 设置模板变量信息
     * 
     * @return 模板列表
     */
    public static VelocityContext prepareContext( CodeGenerator bladeCodeGenerator )
    {

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("serviceName", bladeCodeGenerator.getServiceName());
        velocityContext.put("port", bladeCodeGenerator.getPort());
        velocityContext.put( "packageName" , bladeCodeGenerator.getPackageName() );
        return velocityContext;
    }

    /**
     * 获取模板信息
     * 
     * @return 模板列表
     */
    public static List<Map<String,String>> getTemplateList( CodeGenerator bladeCodeGenerator )
    {
        List<Map<String,String>> templates = new ArrayList<Map<String,String>>();
        templates.add( Collections.singletonMap("templates/cfg/pom.xml.vm", bladeCodeGenerator.getProjectOutputDir() + "/pom.xml" ));
        templates.add( Collections.singletonMap("templates/cfg/application.info.txt.vm", bladeCodeGenerator.getYmlOutputDir() + "/application.info.txt" ));
        templates.add( Collections.singletonMap("templates/cfg/application-dev.yml.vm", bladeCodeGenerator.getYmlOutputDir() + "/application-dev.yml" ));
        templates.add( Collections.singletonMap("templates/cfg/application-test.yml.vm", bladeCodeGenerator.getYmlOutputDir() + "/application-test.yml" ));
        templates.add( Collections.singletonMap("templates/cfg/application-prod.yml.vm", bladeCodeGenerator.getYmlOutputDir() + "/application-prod.yml" ));
        templates.add( Collections.singletonMap("templates/application.java.vm", bladeCodeGenerator.getCodeOutputDir() + "/" + bladeCodeGenerator.getPackageName().replace(".", "/") + "/Application.java" ));
        
        return templates;
    }

}