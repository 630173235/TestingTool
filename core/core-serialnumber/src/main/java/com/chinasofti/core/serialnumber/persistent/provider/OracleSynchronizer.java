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
public class OracleSynchronizer extends AbstractJdbcSynchronizer implements SeqSynchronizer {

	// @formatter:off
	private final static String ORACLE_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                "seq_name NVARCHAR2 ( 255 ) NOT NULL," +
                "seq_partition NVARCHAR2 ( 255 ) NOT NULL," +
                "seq_next_value NUMBER(10) NOT NULL," +
                "seq_create_time TIMESTAMP NOT NULL," +
                "seq_update_time TIMESTAMP," +
                "constraint TB_EX_PK primary key ( seq_name, seq_partition ) " +
            ")";

	private final static String ORACLE_DROP_TABLE = "DROP TABLE $TABLE_NAME";

	private final static String ORACLE_INSERT =
			"INSERT INTO $TABLE_NAME" +
					"(seq_name,seq_partition,seq_next_value,seq_create_time)" +
					" VALUES (?,?,?,sysdate)";

	private final static String ORACLE_UPDATE_VALUE =
			"UPDATE $TABLE_NAME SET seq_next_value=?,seq_update_time = sysdate " +
					"WHERE seq_name=? AND seq_partition=? AND seq_next_value=?";

	private final static String ORACLE_SELECT_VALUE =
			"SELECT seq_next_value FROM $TABLE_NAME WHERE seq_name=? AND seq_partition=?";

    // @formatter:on

	private final String tableName;

	private final JdbcTemplate jdbcTemplate;

	@Override
	protected String getCreateTableSql() {
		return null;
	}

	@Override
	protected String getDropTableSql() {
		return ORACLE_DROP_TABLE.replace("$TABLE_NAME", tableName);
	}

	@Override
	protected String getCreateSeqSql() {
		return ORACLE_INSERT.replace("$TABLE_NAME", tableName);
	}

	@Override
	protected String getSelectSeqSql() {
		return ORACLE_SELECT_VALUE.replace("$TABLE_NAME", tableName);
	}

	@Override
	protected String getUpdateSeqSql() {
		return ORACLE_UPDATE_VALUE.replace("$TABLE_NAME", tableName);
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
