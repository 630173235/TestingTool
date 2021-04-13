
package com.chinasofti.core.secure;

import lombok.Data;

/**
 * TokenInfo
 *
 * @author Arvin Zhou
 */
@Data
public class TokenInfo {

	/**
	 * 令牌值
	 */
	private String token;

	/**
	 * 过期秒数
	 */
	private int expire;
	
	/**
	 * 过期时间戳
	 */
	private long expireTime;
	
	

}
