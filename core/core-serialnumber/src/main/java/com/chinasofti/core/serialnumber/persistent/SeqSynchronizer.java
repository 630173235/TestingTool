package com.chinasofti.core.serialnumber.persistent;

import java.util.Optional;

public interface SeqSynchronizer {

	/**
	 * 创建序号记录,如已经存在则忽略
	 * @param name 名称
	 * @param partition 分区
	 * @param nextValue 初始值
	 * @return true 表示创建成功,false 表示记录已经存在
	 */
	boolean tryCreate(String name, String partition, long nextValue);

	/**
	 * 尝试更新记录
	 * <p>
	 * <b>此接口为可选实现接口</b>
	 * </p>
	 * @param name
	 * @param partition
	 * @param nextValueOld
	 * @param nextValueNew
	 * @return true 表示更新成功
	 * @throws UnsupportedOperationException 不支持此方法
	 */
	boolean tryUpdate(String name, String partition, long nextValueOld, long nextValueNew);

	/**
	 * 尝试加法操作
	 * @param name
	 * @param partition
	 * @param delta 加数
	 * @param maxReTry 最大重试次数,小于0表示无限制. 0 表示重试零次(总共执行1次) 1 表示重试一次(总共执行2次)。此参数跟具体的实现层有关。
	 * @return 返回执行加法操作执行结果
	 */
	AddState tryAddAndGet(String name, String partition, int delta, int maxReTry);

	/**
	 * 查询当前值
	 * @param name
	 * @param partition
	 * @return 返回null表示记录不存在
	 */
	Optional<Long> getNextValue(String name, String partition);

	/**
	 * 执行初始化.
	 * <p>
	 * <b>无线程线程安全保障,但是可以多次执行</b>
	 * </p>
	 * 。
	 */
	void init();

	/**
	 * 关闭,执行资源清理.
	 * <p>
	 * <b>无线程线程安全保障,但是可以多次执行</b>
	 * </p>
	 * 。
	 */
	default void shutdown() {
		// do nothing
	}

	/**
	 * 查询语句总共执行的次数
	 * @return
	 */
	default long getQueryCounter() {
		return 0L;
	}

	/**
	 * 更新语句总共执行的次数
	 * @return
	 */
	default long getUpdateCounter() {
		return 0L;
	}

	long trySelect(String name, String partition);
}
