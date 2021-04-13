package com.chinasofti.core.changelog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 数据日志注解
 * </p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataChangeLog {
    /**
     * sPel表达式1
     */
    //String sPel1() default "";

    /**
     * sPel表达式2
     */
    //String sPel2() default "";

    /**
     * sPel表达式3
     */
    //String sPel3() default "";

    /**
     * <p>
     * 类型
     * </p>
     */
    String type() default "";

    /**
     * <p>
     * 标签
     * </p>
     */
    String tag() default "";

    /**
     * <p>
     * 注释
     * </p>
     */
    String note() default "";
}
