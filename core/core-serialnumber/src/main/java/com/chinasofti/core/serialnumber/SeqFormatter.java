package com.chinasofti.core.serialnumber;

/**
 * 格式化函数
 */
@FunctionalInterface
public interface SeqFormatter {

	/**
	 * 默认格式: 分区+序号值
	 */
	SeqFormatter DEFAULT_FORMAT = (seqName, partition, value) -> String.format("%s%05d", partition, value);

	/**
	 * 适用于按年分区
	 */
	SeqFormatter ANNUALLY_FORMAT = (seqName, partition, value) -> String.format("%s%10d", partition, value);

	/**
	 * 适用于按月分区
	 */
	SeqFormatter MONTHLY_FORMAT = (seqName, partition, value) -> String.format("%s%08d", partition, value);

	/**
	 * 适用于按日分区
	 */
	SeqFormatter DAILY_FORMAT = (seqName, partition, value) -> String.format("%s%06d", partition, value);

	/**
	 * 格式化
	 * @param seqName
	 * @param partition
	 * @param value
	 * @return
	 */
	String format(String seqName, String partition, long value);

}
