package com.chinasofti.core.serialnumber.persistent;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

import com.chinasofti.core.serialnumber.LongSeqPool;
import com.chinasofti.core.serialnumber.SeqFormatter;
import com.chinasofti.core.serialnumber.Sequence;
import com.chinasofti.core.serialnumber.exception.SeqException;

/**
 * 取号器
 */
public class SeqHolder implements Sequence<Long> {

	public LongSeqPool getSeqPool() {
		if( seqPool == null )
			seqPool = fetch();
		return seqPool;
	}

	public void setSeqPool(LongSeqPool seqPool) {
		this.seqPool = seqPool;
	}

	private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

	private final Lock rLock = rwLock.readLock();

	private final Lock wLock = rwLock.writeLock();

	private final SeqSynchronizer seqSynchronizer;

	private final String name;

	private final Supplier<String> partitionFunc;

	private final long initValue;

	private final int poolSize;

	private final SeqFormatter seqFormatter;

	private final AtomicLong pollCount = new AtomicLong();

	private final AtomicReference<String> currentPartitionRef = new AtomicReference<>();

	private volatile LongSeqPool seqPool;

	/**
	 * 构造方法
	 * @param seqSynchronizer 同步器
	 * @param name 名称
	 * @param partitionFunc 分区函数
	 * @param initValue 初始值,号池不存在时使用
	 * @param poolSize 表示单次申请序号数量
	 * @param seqFormatter 自定义格式化输出，可选
	 */
	public SeqHolder(SeqSynchronizer seqSynchronizer, String name, Supplier<String> partitionFunc, long initValue,
			int poolSize, SeqFormatter seqFormatter) {
		this.seqSynchronizer = seqSynchronizer;
		this.name = name;
		this.partitionFunc = partitionFunc;
		this.initValue = initValue;
		this.poolSize = poolSize;
		this.seqFormatter = seqFormatter == null ? SeqFormatter.DEFAULT_FORMAT : seqFormatter;
	}

	/**
	 * 构造方法
	 * @param seqSynchronizer 同步器
	 * @param name 名称
	 * @param partition 分区名称
	 * @param initValue 初始值,号池不存在时使用
	 * @param poolSize 表示单次申请序号数量
	 * @param seqFormatter 自定义格式化输出，可选
	 */
	public SeqHolder(SeqSynchronizer seqSynchronizer, String name, String partition, long initValue, int poolSize,
			SeqFormatter seqFormatter) {
		this(seqSynchronizer, name, () -> partition, initValue, poolSize, seqFormatter);
	}

	@Override
	public String getName() {
		rLock.lock();
		try {
			return seqPool == null ? name : seqPool.getName();
		}
		finally {
			rLock.unlock();
		}
	}

	@Override
	public Optional<Long> nextOpt() {
		Optional<Long> val;
		rLock.lock();
		try {
			if (partitionFunc.get().equals(currentPartitionRef.get())) {
				val = (seqPool == null ? Optional.empty() : seqPool.nextOpt());
				if (val.isPresent()) {
					return val;
				}
			}
		}
		finally {
			rLock.unlock();
		}
		return pull();
	}

	@Override
	public Long next() {
		return nextOpt().get();
	}

	@Override
	public Optional<String> nextStrOpt() throws SeqException {
		return nextOpt().map(n -> seqFormatter.format(name, currentPartitionRef.get(), n));
	}

	/**
	 * 默认的初始化是懒加载,执行此方法可以手动初始化
	 */
	public void prepare() {
		wLock.lock();
		try {
			if (seqPool == null) {
				seqPool = fetch();
			}
		}
		finally {
			wLock.unlock();
		}
	}

	/**
	 * 从后端拉取值的次数
	 * @return
	 */
	public long getPullCount() {
		return pollCount.get();
	}

	private final Optional<Long> pull() {
		Optional<Long> val;
		wLock.lock();
		try {
			if (seqPool == null || !partitionFunc.get().equals(currentPartitionRef.get())) {
				seqPool = fetch();
			}
			val = seqPool.nextOpt();
			if (!val.isPresent()) {
				val = (seqPool = fetch()).nextOpt();
				if (!val.isPresent()) {
					throw new IllegalStateException("Bug detected : " + seqPool.toString());
				}
			}
			return val;
		}
		finally {
			wLock.unlock();
		}
	}

	private LongSeqPool fetch() {
		pollCount.incrementAndGet();
		LongSeqPool seqPool;
		String partition = partitionFunc.get();
		if (seqSynchronizer.tryCreate(name, partition, initValue + poolSize)) {
			seqPool = LongSeqPool.forRange(makePoolName(name, partition), initValue, initValue + poolSize - 1, false);
		}
		else {
			AddState state = seqSynchronizer.tryAddAndGet(name, partition, poolSize, -1);
			seqPool = LongSeqPool.forRange(makePoolName(name, partition), state.getPrevious(), state.getCurrent() - 1,
					false);
		}
		currentPartitionRef.set(partition);
		return seqPool;
	}

	private String makePoolName(String seqName, String window) {
		return seqName + "/" + window;
	}

	@Override
	public boolean serializeCurrent() {
		// TODO Auto-generated method stub
	    if( this.seqPool != null )
	    {
	    	Long currentValue = this.seqPool.peek();
			String partition = partitionFunc.get();
			long lastValue = seqSynchronizer.trySelect(name, partition);
			return seqSynchronizer.tryUpdate(name, partition, lastValue , currentValue);
	    }
	    else
	    	return false;
	}

}
