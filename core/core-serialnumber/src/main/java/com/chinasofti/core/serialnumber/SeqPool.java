package com.chinasofti.core.serialnumber;

/**
 * 序号池
 *
 */
public interface SeqPool<T, S extends SeqPool<T, S>> extends Sequence<T> {

	/**
	 * 查看当前计数器的值,注意此方法不能保证取值的有效性
	 * @return
	 */
	T peek();

	/**
	 * 是否还能取值
	 * @return
	 */
	boolean hasMore();

	/**
	 * 分离,得到一个新的{@code SeqPool},其起始值就是本实例的当前值
	 * @param name 新实例的名称
	 * @return
	 */
	S fork(String name);

	/**
	 * 号池中的剩余序号数量
	 * @return
	 */
	long remaining();

	/**
	 * 号池的容量（容量与用量无关）
	 * @return
	 */
	long capacity();

	/**
	 * 最小值
	 * @return
	 */
	T minValue();

	/**
	 * 最大值
	 * @return
	 */
	T maxValue();

}
