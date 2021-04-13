package com.chinasofti.core.changelog.handle;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.chinasofti.core.changelog.entity.DataLog;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultDataLogHandler implements DataLogHandler{
	
	private final JdbcTemplate jdbcTemplate;
	
	private static String LOG_SAVE = "insert into sys_log_datachange( tenant_id , operation_type , module , tag , note , ip , old_data , new_data , "
			+ "data_diff , create_dept , create_by )  value( ? , ? , ? , ? , ? , ? , ? ,? , ? , ? , ? )";
	
	private static int[] LOG_ARG_TYPES = { Types.VARCHAR , Types.VARCHAR ,Types.VARCHAR , 
			Types.VARCHAR , Types.VARCHAR , Types.VARCHAR , Types.LONGVARCHAR , Types.LONGVARCHAR , Types.LONGVARCHAR , Types.VARCHAR , Types.VARCHAR };
	@Override
	public void log(List<DataLog> dataLogs) {
		// TODO Auto-generated method stub
		List<Object[]> batchArgs = new ArrayList<>();
		for( DataLog datalog : dataLogs )
		{
			 batchArgs.add(new Object[]{datalog.getTenantId() , datalog.getOperationType() , datalog.getMoudle() , datalog.getTag() ,
					 datalog.getNote() , datalog.getId() , datalog.getOldData() , datalog.getNewData() , datalog.getDataDiff() , datalog.getCreateDept() , datalog.getCreateBy() });
		}
		jdbcTemplate.batchUpdate(LOG_SAVE, batchArgs, LOG_ARG_TYPES );
	}

}
