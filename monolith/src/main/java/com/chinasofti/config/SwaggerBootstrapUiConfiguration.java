package com.chinasofti.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerBootstrapUiConfiguration implements WebMvcConfigurer{
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
		    registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
	        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
	        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	 }
}
