
package com.chinasofti.core.boot.file;

import java.io.File;

/**
 * 文件代理接口
 *
 * @author Arvin Zhou
 */
public interface IFileProxy {

	/**
	 * 返回路径[物理路径][虚拟路径]
	 *
	 * @param file 文件
	 * @param dir  目录
	 * @return String
	 */
	String[] path(File file, String dir);

	/**
	 * 文件重命名策略
	 *
	 * @param file 文件
	 * @param path 路径
	 * @return File
	 */
	File rename(File file, String path);

	/**
	 * 图片压缩
	 *
	 * @param path 路径
	 */
	void compress(String path);

}
