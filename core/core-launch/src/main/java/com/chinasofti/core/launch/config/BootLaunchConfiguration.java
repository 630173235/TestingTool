
package com.chinasofti.core.launch.config;

import lombok.AllArgsConstructor;
import com.chinasofti.core.launch.props.BootProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 配置类
 *
 * @author Arvin Zhou
 */
@Configuration
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({
	BootProperties.class
})
public class BootLaunchConfiguration {

}
