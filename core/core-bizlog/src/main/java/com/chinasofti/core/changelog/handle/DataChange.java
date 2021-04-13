package com.chinasofti.core.changelog.handle;

import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据变化
 * </p>
 */
@Data
public class DataChange {
    /**
     * sqlSessionFactory
     */
    private SqlSessionFactory sqlSessionFactory;
    /**
     * sqlStatement
     */
    private String sqlStatement;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 对应实体类
     */
    private Class<?> entityType;
    /**
     * 更新前数据
     */
    private List<?> oldData;
    /**
     * 更新后数据
     */
    private List<?> newData;
    
    private String type;
    
    private Map historyQuery;
}
