package com.chinasofti.core.serialnumber.persistent.provider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.chinasofti.core.serialnumber.exception.SeqException;
import com.chinasofti.core.serialnumber.persistent.SeqSynchronizer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * MySql支持
 */
@Slf4j
@AllArgsConstructor
public class MySqlSynchronizer extends AbstractJdbcSynchronizer implements SeqSynchronizer {

	// @formatter:off

	private final static String MYSQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "seq_name VARCHAR ( 255 ) NOT NULL," +
                "seq_partition VARCHAR ( 255 ) NOT NULL," +
                "seq_next_value BIGINT NOT NULL," +
                "seq_create_time TIMESTAMP NOT NULL," +
                "seq_update_time TIMESTAMP NULL," +
                "PRIMARY KEY ( `seq_name`, `seq_partition` ) " +
            ")";

	private final static String MYSQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME";

	private final static String MYSQL_INSERT =
			"INSERT INTO $TABLE_NAME" +
					"(seq_name,seq_partition,seq_next_value,seq_create_time)" +
					" VALUES (?,?,?,now())";

	private final static String MYSQL_UPDATE_VALUE =
			"UPDATE $TABLE_NAME SET seq_next_value=?,seq_update_time=now() " +
					"WHERE seq_name=? AND seq_partition=? AND seq_next_value=?";

	private final static String MYSQL_SELECT_VALUE =
			"SELECT seq_next_value FROM $TABLE_NAME WHERE seq_name=? AND seq_partition=?";

    // @formatter:on

	private final String tableName;

	private final JdbcTemplate jdbcTemplate;

	@Override
	protected String getCreateTableSql() {
		return MYSQL_CREATE_TABLE.replace("$TABLE_NAME", tableName);
	}

	@Override
	protected String getDropTableSql() {
		return MYSQL_DROP_TABLE.replace("$TABLE_NAME", tableName);
	}

	@Override
	protected String getCreateSeqSql() {
		return MYSQL_INSERT.replace("$TABLE_NAME", tableName);
	}

	@Override
	protected String getSelectSeqSql() {
		return MYSQL_SELECT_VALUE.replace("$TABLE_NAME", tableName);
	}

	@Override
	protected String getUpdateSeqSql() {
		return MYSQL_UPDATE_VALUE.replace("$TABLE_NAME", tableName);
	}

	@Override
	protected JdbcTemplate getJdbcTemplate() {
		// TODO Auto-generated method stub
		return this.jdbcTemplate;
	}

	@Override
	protected int[] getCreateSeqSqlArgTypes() {
		// TODO Auto-generated method stub
		int[] argTypes= { Types.VARCHAR , Types.VARCHAR ,Types.BIGINT };
		return argTypes;
	}

	@Override
	protected int[] getSelectSeqSqlArgTypes() {
		// TODO Auto-generated method stub
		int[] argTypes = { Types.VARCHAR , Types.VARCHAR };
		return argTypes;
	}

	@Override
	protected int[] getUpdateSeqSqlArgTypes() {
		// TODO Auto-generated method stub
		int[] argTypes= { Types.BIGINT , Types.VARCHAR , Types.VARCHAR ,Types.BIGINT };
		return argTypes;
	}

}
