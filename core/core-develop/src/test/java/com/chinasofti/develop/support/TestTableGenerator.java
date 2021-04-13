package com.chinasofti.develop.support;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class TestTableGenerator {

	@Test
	void testRun()
	{
		TableGenerator tableGenerator = new TableGenerator();
		tableGenerator.setDropOldTable( true );
		Table table = new Table();
		table.setCode( "my_test" );
		table.setName( "测试的表" );
		
		Column column1 = new Column();
		column1.setCode( "column1" );
		column1.setName( "字段1" );
		column1.setDataType( "integer" );
		
		Column column2 = new Column();
		column2.setCode( "column2" );
		column2.setName( "字段2" );
		column2.setDataType( "number" );
		
		Column column3 = new Column();
		column3.setCode( "column3" );
		column3.setName( "字段3" );
		column3.setDataType( "string" );
		
		Column column4 = new Column();
		column4.setCode( "column4" );
		column4.setName( "字段4" );
		column4.setDataType( "datetime" );
		
		Column column5 = new Column();
		column5.setCode( "column5" );
		column5.setName( "字段5" );
		column5.setDataType( "long" );
		column5.setIsPrimaryKey( true );
		
		Column column6 = new Column();
		column6.setCode( "column6" );
		column6.setName( "字段6" );
		column6.setDataType( "timestamp" );
		
		Column column7 = new Column();
		column7.setCode( "column7" );
		column7.setName( "字段7" );
		column7.setDataType( "text" );
		
		Column column8 = new Column();
		column8.setCode( "column8" );
		column8.setName( "字段8" );
		column8.setDataType( "boolean" );
		
		List<Column> columns = new ArrayList<Column>();
		columns.add( column1 );
		columns.add( column2 );
		columns.add( column3 );
		columns.add( column4 );
		columns.add( column5 );
		columns.add( column6 );
		columns.add( column7 );
		columns.add( column8 );
		
		table.setColumns( columns );
		table.setHasSuperEntity( true );
		List<Table> tables = new ArrayList<Table>();
		tables.add( table );
		tableGenerator.setTables( tables );
		tableGenerator.setDriverName( "com.mysql.cj.jdbc.Driver" );
		tableGenerator.setUrl( "jdbc:mysql://127.0.0.1:3306/blade?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true" );
		tableGenerator.setUsername( "root" );
		tableGenerator.setPassword( "zx883408" );
		tableGenerator.run();
	}
	
	@Test
	//integer,number,string,datetime,long,timestamp,text,boolean
	void testGenerator()
	{
		TableGenerator tableGenerator = new TableGenerator();
		tableGenerator.setDropOldTable( true );
		Table table = new Table();
		table.setCode( "my_test" );
		table.setName( "测试的表" );
		
		Column column1 = new Column();
		column1.setCode( "column1" );
		column1.setName( "字段1" );
		column1.setDataType( "integer" );
		
		Column column2 = new Column();
		column2.setCode( "column2" );
		column2.setName( "字段2" );
		column2.setDataType( "number" );
		
		Column column3 = new Column();
		column3.setCode( "column3" );
		column3.setName( "字段3" );
		column3.setDataType( "string" );
		
		Column column4 = new Column();
		column4.setCode( "column4" );
		column4.setName( "字段4" );
		column4.setDataType( "datetime" );
		
		Column column5 = new Column();
		column5.setCode( "column5" );
		column5.setName( "字段5" );
		column5.setDataType( "long" );
		column5.setIsPrimaryKey( true );
		
		Column column6 = new Column();
		column6.setCode( "column6" );
		column6.setName( "字段6" );
		column6.setDataType( "timestamp" );
		
		Column column7 = new Column();
		column7.setCode( "column7" );
		column7.setName( "字段7" );
		column7.setDataType( "text" );
		
		Column column8 = new Column();
		column8.setCode( "column8" );
		column8.setName( "字段8" );
		column8.setDataType( "boolean" );
		
		List<Column> columns = new ArrayList<Column>();
		columns.add( column1 );
		columns.add( column2 );
		columns.add( column3 );
		columns.add( column4 );
		columns.add( column5 );
		columns.add( column6 );
		columns.add( column7 );
		columns.add( column8 );
		
		table.setColumns( columns );
		table.setHasSuperEntity( true );
		List<Table> tables = new ArrayList<Table>();
		tables.add( table );
		System.out.println( tableGenerator.getCreateTabelSqlForMySql( tables ) );
	}

}
