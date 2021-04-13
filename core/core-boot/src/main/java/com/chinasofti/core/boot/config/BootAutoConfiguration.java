
package com.chinasofti.core.boot.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.chinasofti.core.launch.props.BootProperties;
import com.chinasofti.core.tool.constant.SystemConstant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 配置类
 *
 * @author Arvin Zhou
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({
	BootProperties.class
})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@AllArgsConstructor
public class BootAutoConfiguration {

	private BootProperties bootProperties;

	/**
	 * 全局变量定义
	 *
	 * @return SystemConstant
	 */
	@Bean
	public SystemConstant fileConst() {
		SystemConstant me = SystemConstant.me();

		//设定开发模式
		me.setDevMode(("dev".equals(bootProperties.getEnv())));

		//设定文件上传远程地址
		me.setDomain(bootProperties.get("upload-domain", "http://localhost:8888"));

		//设定文件上传是否为远程模式
		me.setRemoteMode(bootProperties.getBoolean("remote-mode", true));

		//远程上传地址
		me.setRemotePath(bootProperties.get("remote-path", System.getProperty("user.dir") + "/work/boot"));

		//设定文件上传头文件夹
		me.setUploadPath(bootProperties.get("upload-path", "/upload"));

		//设定文件下载头文件夹
		me.setDownloadPath(bootProperties.get("download-path", "/download"));

		//设定上传图片是否压缩
		me.setCompress(bootProperties.getBoolean("compress", false));

		//设定上传图片压缩比例
		me.setCompressScale(bootProperties.getDouble("compress-scale", 2.00));

		//设定上传图片缩放选择:true放大;false缩小
		me.setCompressFlag(bootProperties.getBoolean("compress-flag", false));

		return me;
	}

}
