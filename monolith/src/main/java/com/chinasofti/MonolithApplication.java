package com.chinasofti;

import com.chinasofti.core.launch.BootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动器
 *
 * @author Arvin
 */
@EnableScheduling
@SpringBootApplication
public class MonolithApplication {

	public static void main(String[] args) {
		BootApplication.run( null , MonolithApplication.class, args);
	}

}

