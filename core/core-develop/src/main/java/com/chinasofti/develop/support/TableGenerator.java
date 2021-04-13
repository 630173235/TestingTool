package com.chinasofti.develop.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.chinasofti.core.tool.utils.StringUtil;
import com.baomidou.mybatisplus.annotation.DbType;

import lombok.Data;

@Data
public class TableGenerator {

	private static final Logger log = LoggerFactory.getLogger(TableGenerator.class);
	
	/**
	 * 数据库驱动名
	 */
	private String driverName;
	/**
	 * 数据库链接地址
	 */
	private String url;
	/**
	 * 数据库用户名
	 */
	private String username;
	/**
	 * 数据库密码
	 */
	private String password;
	
	private List<Table> tables;

	private boolean isDropOldTable;
	
	public void run()
	{
		if (StringUtil.containsAny(driverName, DbType.MYSQL.getDb())) {
			executeSql( getCreateTabelSqlForMySql( tables ) );
		}
		else if (StringUtil.containsAny(driverName, DbType.POSTGRE_SQL.getDb())) 
		{
			//executeSql( getCreateTabelSqlForMySql( tables ) );
		}
		else if( StringUtil.containsAny(driverName, DbType.ORACLE.getDb()) )	
		{
			//executeSql( getCreateTabelSqlForMySql( tables ) );
		}
		else
		{
			log.error( "不能识别的数据库驱动类型 ： " + driverName );
		}
	}
	
	public List<String> getCreateTabelSqlForMySql( List<Table> tables )
	{
		List<String> sqlList = new ArrayList<String>();
		List<String> tableName = new ArrayList<String>();
		for( Table table : tables )
		{
			StringBuffer sql = new StringBuffer(); 
			if( tableName.isEmpty() )  
				tableName.add( table.getCode() );
			else if( tableName.contains( table.getName() ) )
				continue;
			if( isDropOldTable )
				sqlList.add( "DROP TABLE IF EXISTS " + table.getCode());
			sql.append( "CREATE TABLE " + table.getCode() + "(\n" );
			String primaryKeyName = null;
			for( Column cloumn : table.getColumns() )
			{
				if( primaryKeyName == null && cloumn.getIsPrimaryKey() )
					primaryKeyName =  cloumn.getCode() ;
				List<String> cloumnName = new ArrayList<String>();
				if( cloumnName.isEmpty() )  
					cloumnName.add( cloumn.getCode() );
				else if( cloumnName.contains( cloumn.getCode() ) )
					continue;
				switch( cloumn.getDataType() )
				{
				    case "integer":
				    {
				    	sql.append( cloumn.getCode() ).append( " int " ).append( cloumn.getDataLen() != null ? "(" + cloumn.getDataLen() + ") " : "" )
				    	.append( cloumn.getIsNull() ? " NULL DEFAULT NULL " : " NOT NULL " ).append(  " COMMENT '" + cloumn.getName() + "',\n" );
				    	break;
				    }
				    case "number":
				    {
				    	sql.append( cloumn.getCode() ).append( " decimal " ).append( "(" + cloumn.getDataLen() + "," +  cloumn.getDatePrecision() + ") " )
				    	.append( cloumn.getIsNull() ? " NULL DEFAULT NULL " : " NOT NULL " ).append(  " COMMENT '" + cloumn.getName() + "',\n" );
				    	break;
				    }
				    case "string":
				    {
				    	sql.append( cloumn.getCode() ).append( " varchar " ).append( "(" + cloumn.getDataLen() + ") " )
				    	.append( cloumn.getIsNull() ? " NULL DEFAULT NULL " : " NOT NULL " ).append(  " COMMENT '" + cloumn.getName() + "',\n" );
				    	break;
				    }
				    case "datetime":
				    {
				    	sql.append( cloumn.getCode() ).append( " datetime " )
				    	.append( cloumn.getIsNull() ? " NULL DEFAULT NULL " : " NOT NULL " ).append(  " COMMENT '" + cloumn.getName() + "',\n" );
				    	break;
				    }
				    case "long":
				    {
				    	sql.append( cloumn.getCode() ).append( " bigint" ).append( "(" + cloumn.getDataLen() + ")" )
				    	.append( cloumn.getIsNull() ? " NULL DEFAULT NULL " : " NOT NULL " ).append(  " COMMENT '" + cloumn.getName() + "',\n" );
				    	break;
				    }
				    case "timestamp":
				    {
				    	sql.append( cloumn.getCode() ).append( " timestamp " )
				    	.append( cloumn.getIsNull() ? " NULL DEFAULT NULL " : " NOT NULL " ).append(  " COMMENT '" + cloumn.getName() + "',\n" );
				    	break;
				    }
				    case "text":
				    {
				    	sql.append( cloumn.getCode() ).append( " text " )
				    	.append( cloumn.getIsNull() ? " NULL DEFAULT NULL " : " NOT NULL " ).append(  " COMMENT '" + cloumn.getName() + "',\n" );
				    	break;
				    }
				    case "boolean":
				    {
				    	sql.append( cloumn.getCode() ).append( " tinyint" ).append( "(1)" )
				    	.append( cloumn.getIsNull() ? " NULL DEFAULT NULL " : " NOT NULL " ).append(  " COMMENT '" + cloumn.getName() + "',\n" );
				    	break;
				    }
				    default : break;
				}
			}
			
			if( table.getHasSuperEntity() )
			{
				sql.append( "create_user bigint(64) DEFAULT NULL COMMENT '创建人',\n" );
				sql.append( "create_dept bigint(64) DEFAULT NULL COMMENT '创建部门',\n" );
				sql.append( "create_time datetime DEFAULT NULL COMMENT '创建时间',\n" );
				sql.append( "update_user bigint(64) DEFAULT NULL COMMENT '修改人',\n" );
				sql.append( "update_time datetime DEFAULT NULL COMMENT '修改时间',\n" );
				sql.append( "is_deleted int(2) NOT NULL COMMENT '是否已删除',\n" );
				sql.append( "status int(2) DEFAULT NULL COMMENT '业务状态',\n" );
			}
			
			sql.append( "PRIMARY KEY (" + primaryKeyName + ") USING BTREE \n" );
			sql.append( ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='" + table.getName() +"' \n" );
			sqlList.add( sql.toString() );
		}
		return sqlList;
	}
	
	private void executeSql( List<String> sqlList ) 
	{
		Connection connection = null;
		Statement statement = null;
		try
		{
			Class.forName(driverName);
			connection = DriverManager.getConnection( url, username, password );
			connection.setAutoCommit(false);
			statement=connection.createStatement();
			for( String sql : sqlList )
			{	
				statement.executeUpdate( sql );
			}
			connection.commit();
		}
		catch( Exception e )
		{
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally
		{
			if( statement != null )
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if( connection != null )
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
		
		
	}
}
