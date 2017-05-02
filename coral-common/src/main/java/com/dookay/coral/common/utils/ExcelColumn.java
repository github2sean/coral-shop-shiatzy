package com.dookay.coral.common.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    /**
     * 列名
     */
    String name() default "";

    /**
     * 格式化字符串
     */
    String format() default "";


    /**
     * 字段的类型
     */
    Class type() default Object.class;

    /**
     * 是否以json形式转化
     * @return
     */
    boolean json() default false;
}
