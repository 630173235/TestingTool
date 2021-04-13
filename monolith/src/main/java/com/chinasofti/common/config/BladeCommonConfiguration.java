
package com.chinasofti.common.config;


import lombok.AllArgsConstructor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 公共封装包配置类
 *
 *  @author Arvin Zhou
 */
@Configuration
@AllArgsConstructor
@MapperScan( {"com.chinasofti.**.mapper.**","com.chinasofti.**.mapper.**","com.demo.**.mapper.**"} )
public class BladeCommonConfiguration {

}
