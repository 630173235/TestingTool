package com.chinasofti.core.tool.support.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chinasofti.core.tool.api.R;
import com.chinasofti.core.tool.api.ResultCode;
import com.chinasofti.core.tool.constant.BootConstant;
import com.chinasofti.core.tool.jackson.JsonUtil;
import com.chinasofti.core.tool.utils.ServletUtils;


public class FileUploadInterceptor extends HandlerInterceptorAdapter {

	 protected static final Logger logger = LoggerFactory.getLogger(FileUploadInterceptor.class);
	
	 private List<String> allows;
	 
	 public FileUploadInterceptor( String allow )
	 {
		 if( StringUtils.isNotBlank( allow ) )
		 {
			 String[] tmp = allow.split( "," );
			 allows = Arrays.asList(tmp);
			 logger.info( "允许上传的文件 : " + allows );
		 }
		 else
		 {
			 allows = new ArrayList<String>();
		 }
	 }
	 
	 @Override
	 public boolean preHandle(HttpServletRequest request, 
	            HttpServletResponse response, Object handler)throws Exception
	 {
		 boolean flag= true;
	     // 判断是否为文件上传请求
	     if (request instanceof MultipartHttpServletRequest) 
	     {
	            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	            Map<String, MultipartFile> files =  multipartRequest.getFileMap();
	            Iterator<String> iterator = files.keySet().iterator();
	            //对多部件请求资源进行遍历
	            while (iterator.hasNext()) 
	            {
	                String formKey = (String) iterator.next();
	                MultipartFile multipartFile =  multipartRequest.getFile(formKey);
	                String filename=multipartFile.getOriginalFilename();
	                //判断是否为限制文件类型
	                if (! checkFile(filename)) {
	                    //限制文件类型，请求转发到原始请求页面，并携带错误提示信息
	                    
	                    R result = R.fail(ResultCode.UN_AUTHORIZED);
	        			response.setHeader(BootConstant.CONTENT_TYPE_NAME, MediaType.APPLICATION_JSON_VALUE);
	        			response.setCharacterEncoding(BootConstant.UTF_8);
	        			response.setStatus(HttpServletResponse.SC_OK);
	        			try {
	        				response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
	        			} catch (IOException ex) {
	        				logger.error(ex.getMessage());
	        			}
	        			
	                    logger.warn( filename + " 不允许上传");
	                    flag= false;
	                } 
	            }
	    }
	    return flag;
	 }
	 
	 
	 private boolean checkFile(String fileName)
	 {
	    // 获取文件后缀
	    String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	    if (allows.contains(suffix.trim().toLowerCase()))
	    {
	       return true;
	    }
	    return false;
	 }
}
