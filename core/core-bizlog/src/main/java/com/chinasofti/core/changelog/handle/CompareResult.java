package com.chinasofti.core.changelog.handle;

import lombok.Data;

/**
 * <p>
 * 对比两个对象结果
 * </p>
 */
@Data
public class CompareResult {
    /**
     * 主键id值
     */
    private Long id;
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段注释
     */
    private String fieldComment;
    /**
     * 字段旧值
     */
    private Object oldValue;
    /**
     * 字段新值
     */
    private Object newValue;
}
