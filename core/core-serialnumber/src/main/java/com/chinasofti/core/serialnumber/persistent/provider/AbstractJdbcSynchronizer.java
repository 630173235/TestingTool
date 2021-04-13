package com.chinasofti.core.serialnumber.persistent.provider;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.chinasofti.core.serialnumber.exception.SeqException;
import com.chinasofti.core.serialnumber.persistent.AddState;
import com.chinasofti.core.serialnumber.persistent.SeqSynchronizer;

/**
 * 基于JDBC的同步抽象
 *
 */
@Slf4j
public abstract class AbstractJdbcSynchronizer implements SeqSynchronizer {

	protected final AtomicLong queryCount = new AtomicLong();

	protected final AtomicLong updateCount = new AtomicLong();

	/**
	 * 建表SQL
	 * @return
	 */
	protected abstract String getCreateTableSql();

	/**
	 * 删表SQL
	 * @return
	 */
	protected abstract String getDropTableSql();

	/**
	 * 创建记录SQL
	 * @return
	 */
	protected abstract String getCreateSeqSql();
	
	protected abstract int[] getCreateSeqSqlArgTypes();

	/**
	 * 查询SQL
	 * @return
	 */
	protected abstract String getSelectSeqSql();
	
	protected abstract int[] getSelectSeqSqlArgTypes();

	/**
	 * 更新SQL
	 * @return
	 */
	protected abstract String getUpdateSeqSql();
	
	protected abstract int[] getUpdateSeqSqlArgTypes();

	/**
	 * 获取数据库连接
	 * @return
	 * @throws SQLException
	 */
	protected abstract JdbcTemplate getJdbcTemplate() ;


	/**
	 * 建表,表已经存在则忽略
	 * @throws SQLException
	 */
	public void createMissingTable() {
		try
		{
			final String sql = getCreateTableSql();
			if( StringUtils.isBlank( sql ) )
			{
				log.warn("初始化语句为空，请确保已经手工进行了初始化");
				return;
			}
			log.debug(sql);
			getJdbcTemplate().update( sql );
		}
		catch (DataAccessException e) {
			log.warn(e.getMessage(), e);
			throw new SeqException(e.getMessage(), e);
		}
	}

	/**
	 * 删表,表不存在则忽略
	 * @throws SQLException
	 */
	public void dropTable() {
		try
		{
			final String sql = this.getDropTableSql();
			log.debug(sql);
			getJdbcTemplate().update( sql );
		}
		catch (DataAccessException e) {
			log.warn(e.getMessage(), e);
			throw new SeqException(e.getMessage(), e);
		}
	}

	/**
	 * 查询当前值
	 * @param name
	 * @param partition
	 * @return 无查询结果(比如Seq记录不存在)返回null
	 * @throws SQLException 数据库异常
	 */
	protected Optional<Long> selectSeqValue(String name, String partition) throws SQLException {
		try
		{
			final String sql = getSelectSeqSql();		
			Object[] args = new Object[]{ name ,partition };
			log.debug(sql);
			log.debug(String.format("param: [%s] [%s]", name, partition));
			Long result = getJdbcTemplate().queryForObject(sql, args, this.getSelectSeqSqlArgTypes(), Long.class);
			if( result != null )
			    return Optional.of(result);
			else
				return Optional.empty();
		}
		catch( EmptyResultDataAccessException ee )
		{
			//log.warn(ee.getMessage(), ee);
			return Optional.empty();
		}
		catch (DataAccessException e) {
			log.warn(e.getMessage(), e);
			throw new SeqException(e.getMessage(), e);
		}
	}

	/**
	 * 创建某个序号的记录，如果该序号已经存在，则忽略
	 * @param name
	 * @param partition
	 * @param nextValue 记录初始值,若记录已经存在,此参数没有实际用途
	 * @return
	 */
	protected boolean createMissingSeqEntry(String name, String partition, long nextValue) {
		
		Optional<Long> lastValue;
		try {
			lastValue = this.selectSeqValue(name, partition);
			if( lastValue.isPresent() == false )
			{
				final String insertSql = getCreateSeqSql();
				log.debug(insertSql);
				Object[] args = new Object[]{ name ,partition , nextValue };
				log.debug(String.format("param: [%s] [%s] [%d]", name, partition,nextValue));
				int rows = this.getJdbcTemplate().update( insertSql, args , this.getCreateSeqSqlArgTypes() );
				return rows > 0;
			}
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.warn(e.getMessage(), e);
			throw new SeqException(e.getMessage(), e);
		}
		
		
	}

	/**
	 * 更新某个序号的记录值
	 * @param name
	 * @param partition
	 * @param nextValueOld 旧的值,用于 {@code MVCC}
	 * @param nextValueNew
	 * @return 更新成功返回true,失败返回false
	 * @throws SQLException 数据库异常
	 * 
	 * private final static String MYSQL_UPDATE_VALUE =
			"UPDATE $TABLE_NAME SET seq_next_value=?,seq_update_time=now() " +
					"WHERE seq_name=? AND seq_partition=? AND seq_next_value=?";
	 */
	protected boolean updateSeqValue(long nextValueNew , String name, String partition, long nextValueOld) {
		final String sql = getUpdateSeqSql();
		log.debug(sql);
		log.debug(String.format("param:[%d] [%s] [%s]  [%d]", nextValueNew , name, partition , nextValueOld ));
		Object[] args = new Object[]{ nextValueNew , name ,partition , nextValueOld };
		int rows = this.getJdbcTemplate().update( sql, args , this.getUpdateSeqSqlArgTypes() );
		return rows > 0;
	}

	@Override
	public long getQueryCounter() {
		return queryCount.get();
	}

	@Override
	public long getUpdateCounter() {
		return updateCount.get();
	}

	@Override
	public boolean tryCreate(String name, String partition, long nextValue) {
		return createMissingSeqEntry(name, partition, nextValue);
	}

	@Override
	public boolean tryUpdate(String name, String partition, long nextValueOld, long nextValueNew) {
		boolean ret = updateSeqValue(nextValueNew,name, partition, nextValueOld);
		updateCount.incrementAndGet();
		return ret;
	}

	@Override
	public AddState tryAddAndGet(String name, String partition, int delta, int maxReTry) {
		int totalOps = 0;
		try{
			do {
				++totalOps;
				long lastValue = selectSeqValue(name, partition).get();
				queryCount.incrementAndGet();
				final long target = lastValue + delta;
				boolean updateDone = updateSeqValue(target,name, partition, lastValue);
				updateCount.incrementAndGet();
				if (updateDone) {
					return AddState.success(lastValue, target, totalOps);
				}
				else
					break;
			}
			while (maxReTry < 0 || totalOps <= maxReTry + 1);
			return AddState.fail(totalOps);
		}
		catch (SQLException e) {
			log.warn(e.getMessage(), e);
			throw new SeqException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Long> getNextValue(String name, String partition) {
		try{
			Optional<Long> ret = selectSeqValue(name, partition);
			queryCount.incrementAndGet();
			return ret;
		}
		catch (SQLException e) {
			throw new SeqException(e.getMessage(), e);
		}
	}

	@Override
	public void init() {
		createMissingTable();
	}

	public long trySelect(String name, String partition) {
		long lastValue;
		try {
			lastValue = selectSeqValue(name, partition).get();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.warn(e.getMessage(), e);
			throw new SeqException(e.getMessage(), e);
		}
		return lastValue;
	}
}
