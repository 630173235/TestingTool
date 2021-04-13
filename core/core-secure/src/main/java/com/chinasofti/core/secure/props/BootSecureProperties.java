
package com.chinasofti.core.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * secure放行额外配置
 *
 * @author Arvin Zhou
 */
@Data
@ConfigurationProperties("boot.secure")
public class BootSecureProperties {

	private final List<ClientSecure> client = new ArrayList<>();

	private final List<String> skipUrl = new ArrayList<>();

}
